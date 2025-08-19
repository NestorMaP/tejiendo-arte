package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.dto.OrderDto;

public interface CartService {
    public CartItemsDto addProductToCart(AddProductInCartDto addProductInCartDto);

    public OrderDto getCartByUserId(Long userId);

    public OrderDto applyCoupon(Long userId, String code);

    public OrderDto changeProductQuantity(AddProductInCartDto addProductInCartDto, int delta);
}
