package com.personal.tejiendoarte.controller.customer;

import com.personal.tejiendoarte.dto.WishlistDto;
import com.personal.tejiendoarte.service.customer.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(wishlistService.addProductToWishlist(wishlistDto));
        } catch (IOException ioException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ioException.getMessage());
        }
    }

}
