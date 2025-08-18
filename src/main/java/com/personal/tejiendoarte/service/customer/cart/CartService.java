package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.entity.CartItems;
import com.personal.tejiendoarte.entity.Order;
import org.springframework.http.ResponseEntity;

public interface CartService {
    public CartItemsDto addProductToCart(AddProductInCartDto addProductInCartDto);

    public OrderDto getCartByUserId(Long userId);

    public OrderDto applyCoupon(Long userId, String code);
}
