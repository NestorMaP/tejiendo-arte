package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.CartItemsDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    public CartItemsDto addProductToCart(AddProductInCartDto addProductInCartDto);
}
