package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
}
