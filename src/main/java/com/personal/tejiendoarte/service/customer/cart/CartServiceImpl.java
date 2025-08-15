package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.entity.CartItems;
import com.personal.tejiendoarte.entity.Order;
import com.personal.tejiendoarte.enums.OrderStatus;
import com.personal.tejiendoarte.repository.CartItemsRepository;
import com.personal.tejiendoarte.repository.OrderRepository;
import com.personal.tejiendoarte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order currentOrder = orderRepository.findByUserIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), currentOrder.getId(), addProductInCartDto.getUserId());
        )
    }

}
