package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.entity.CartItems;
import com.personal.tejiendoarte.entity.Order;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.enums.OrderStatus;
import com.personal.tejiendoarte.repository.CartItemsRepository;
import com.personal.tejiendoarte.repository.OrderRepository;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.repository.UserRepository;
import com.personal.tejiendoarte.utils.mapper.CartItemsMapper;
import com.personal.tejiendoarte.utils.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
