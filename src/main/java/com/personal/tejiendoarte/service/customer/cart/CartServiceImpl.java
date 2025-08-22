package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.dto.PlaceOrderDto;
import com.personal.tejiendoarte.entity.*;
import com.personal.tejiendoarte.enums.OrderStatus;
import com.personal.tejiendoarte.exceptions.ValidationException;
import com.personal.tejiendoarte.repository.*;
import com.personal.tejiendoarte.utils.mapper.CartItemsMapper;
import com.personal.tejiendoarte.utils.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public OrderDto changeProductQuantity(AddProductInCartDto addProductInCartDto, int delta) {
        if (delta == 0) {
            return orderMapper.mapToDto(orderRepository.findByUserIdAndStatus(
                    addProductInCartDto.getUserId(), OrderStatus.PENDING));
        }

        Order currentOrder = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);

        CartItems cartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), currentOrder.getId(), addProductInCartDto.getUserId())
                .orElseThrow(() -> new ValidationException("Product not found."));

        long newQuantity = cartItem.getQuantity() + delta;

        if (newQuantity <= 0) {
            cartItemsRepository.delete(cartItem);
            currentOrder.getCartItems().removeIf(itemInCart -> itemInCart.getId().equals(cartItem.getId()));
        } else {
            cartItem.setQuantity(newQuantity);
            cartItemsRepository.save(cartItem);
        }

        recomputeTotals(currentOrder);

        return orderMapper.mapToDto(orderRepository.save(currentOrder));

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

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order currentOrder = orderRepository.findByUserIdAndStatus(placeOrderDto.getUserId(), OrderStatus.PENDING);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());

        if (optionalUser.isEmpty()) {
            return null;
        }

        updatePlacedOrder(currentOrder, placeOrderDto);

        createNewCart(optionalUser.get());

        return orderMapper.mapToDto(currentOrder);
    }

    private void updatePlacedOrder(Order currentOrder, PlaceOrderDto placeOrderDto) {
        currentOrder.setDescription(placeOrderDto.getOrderDescription());
        currentOrder.setAddress(placeOrderDto.getAddress());
        currentOrder.setDate(new Date());
        currentOrder.setStatus(OrderStatus.PLACED);

        if (currentOrder.getTrackingId() == null ) currentOrder.setTrackingId(UUID.randomUUID());

        orderRepository.saveAndFlush(currentOrder);
    }

    public void createNewCart(User currentUser) {
        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(currentUser);
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);
    }

    public List<OrderDto> getUserPlacedOrders (Long userId) {
        return orderRepository.findAllByUserIdAndStatusIn(userId,
                List.of(OrderStatus.PLACED, OrderStatus.SHIPPED, OrderStatus.DELIVERED)).stream()
                .map(order -> orderMapper.mapToDto(order)).collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId) throws IOException {
        return orderMapper.mapToDto(orderRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IOException("Order Not Found")));
    }

}
