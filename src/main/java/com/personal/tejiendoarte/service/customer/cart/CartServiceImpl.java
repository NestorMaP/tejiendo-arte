package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.entity.*;
import com.personal.tejiendoarte.enums.OrderStatus;
import com.personal.tejiendoarte.exceptions.ValidationException;
import com.personal.tejiendoarte.repository.*;
import com.personal.tejiendoarte.utils.mapper.CartItemsMapper;
import com.personal.tejiendoarte.utils.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemsMapper cartItemsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CouponRepository couponRepository;

    public CartItemsDto addProductToCart(AddProductInCartDto addProductInCartDto) throws RuntimeException{
        Order currentOrder = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), currentOrder.getId(), addProductInCartDto.getUserId());

        if(optionalCartItems.isPresent()){
            throw new RuntimeException("Product already in cart");
        }

        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

        if(optionalProduct.isEmpty() || optionalUser.isEmpty()){
            throw new RuntimeException("Product or User not found");
        }

        CartItems cartItem = new CartItems();
        updateCartItems(cartItem, currentOrder, optionalProduct.get(), optionalUser.get());

        updateCurrentOrder(currentOrder, cartItem);

        return cartItemsMapper.mapToDto(cartItem);
    }

    private void updateCartItems(CartItems cart, Order currentOrder, Product product, User user) {
        cart.setProduct(product);
        cart.setPrice(product.getPrice());
        cart.setQuantity(1L);
        cart.setUser(user);
        cart.setOrder(currentOrder);

        cartItemsRepository.save(cart);
    }

    private void updateCurrentOrder(Order currentOrder, CartItems cart) {
        currentOrder.setTotalAmount(currentOrder.getTotalAmount() + cart.getPrice());
        currentOrder.setAmount(currentOrder.getAmount() + cart.getPrice());
        currentOrder.getCartItems().add(cart);

        orderRepository.save(currentOrder);
    }

    public OrderDto getCartByUserId(Long userId) {

        return orderMapper.mapToDto(orderRepository.findByUserIdAndStatus(userId, OrderStatus.PENDING));
    }

    public OrderDto applyCoupon(Long userId, String code) {
        Order currentOrder = orderRepository.findByUserIdAndStatus(userId, OrderStatus.PENDING);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found."));

        if(couponIsExpired(coupon)) {
            throw new ValidationException("Coupon is expired.");
        }

        double discountAmount = ((coupon.getDiscount() / 100.0) * currentOrder.getTotalAmount());
        double netAmount = currentOrder.getAmount() - discountAmount;

        currentOrder.setAmount((long)netAmount);
        currentOrder.setDiscount((long)discountAmount);
        currentOrder.setCoupon(coupon);

        return orderMapper.mapToDto(orderRepository.save(currentOrder));
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date couponExpirationDate = coupon.getExpirationDate();

        return couponExpirationDate != null && currentDate.after(couponExpirationDate);
    }

    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        int addedQuantity = 1;
        Order currentOrder = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), currentOrder.getId(), addProductInCartDto.getUserId());

        if (optionalProduct.isEmpty() || optionalCartItems.isEmpty()){ return null; }

        CartItems cartItems = optionalCartItems.get();
        Product product = optionalProduct.get();

        currentOrder.setAmount(currentOrder.getAmount() + product.getPrice());
        currentOrder.setTotalAmount(currentOrder.getTotalAmount() + product.getPrice());

        cartItems.setQuantity(cartItems.getQuantity() + addedQuantity);

        if(currentOrder.getCoupon() != null){
            double discountAmount = ((currentOrder.getCoupon().getDiscount() / 100.0) * currentOrder.getTotalAmount());
            double netAmount = currentOrder.getAmount() - discountAmount;

            currentOrder.setAmount((long)netAmount);
            currentOrder.setDiscount((long)discountAmount);
        }

        cartItemsRepository.save(cartItems);
        orderRepository.save(currentOrder);
        return orderMapper.mapToDto(currentOrder);
    }

    public OrderDto changeProductQuantity(AddProductInCartDto addProductInCartDto, int delta) {
        if (delta == 0) {
            return orderMapper.mapToDto(orderRepository.findByUserIdAndStatus(
                    addProductInCartDto.getUserId(), OrderStatus.PENDING));
        }

        Order order = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Product product = productRepository.findById(addProductInCartDto.getProductId())
                .orElseThrow(() -> new ValidationException("Product not found."));

        CartItems cartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), order.getId(), addProductInCartDto.getUserId())
                .orElseThrow(() -> new ValidationException("Product not found."));

        long newQuantity = cartItem.getQuantity() + delta;

        if (newQuantity <= 0) {
            cartItemsRepository.delete(cartItem);
            order.getCartItems().removeIf(itemInCart -> itemInCart.getId().equals(cartItem.getId()));
        } else {
            cartItem.setQuantity(newQuantity);
            cartItemsRepository.save(cartItem);
        }

        recomputeTotals(order);

        return orderMapper.mapToDto(orderRepository.save(order));

    }

    private void recomputeTotals(Order order) {
        long total = order.getCartItems().stream()
                .mapToLong(itemInCart -> (long) itemInCart.getQuantity() * itemInCart.getProduct().getPrice())
                .sum();

        order.setTotalAmount(total);

        if (order.getCoupon() != null) {
            long couponDiscount = order.getCoupon().getDiscount();
            long discountAmount = Math.round(total * (couponDiscount / 100.0));
            long finalAmount = total - discountAmount;

            order.setDiscount(discountAmount);
            order.setAmount(finalAmount);
        } else {
            order.setDiscount(0L);
            order.setAmount(total);
        }
     }

}
