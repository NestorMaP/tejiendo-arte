package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.entity.CartItems;
import com.personal.tejiendoarte.entity.Order;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.enums.OrderStatus;
import com.personal.tejiendoarte.repository.CartItemsRepository;
import com.personal.tejiendoarte.repository.OrderRepository;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order currentOrder = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), currentOrder.getId(), addProductInCartDto.getUserId());

        if(optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

        if(optionalProduct.isEmpty() || optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
        }

        CartItems cart = new CartItems();
        updateCartItems(cart, currentOrder, optionalProduct.get(), optionalUser.get());

        updateCurrentOrder(currentOrder, cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
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

}
