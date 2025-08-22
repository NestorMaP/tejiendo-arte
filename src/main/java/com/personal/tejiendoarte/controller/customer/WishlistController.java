package com.personal.tejiendoarte.controller.customer;

import com.personal.tejiendoarte.dto.WishlistDto;
import com.personal.tejiendoarte.service.customer.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(wishlistService.getWishlistByUserId(userId));
    }

    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(wishlistService.addProductToWishlist(wishlistDto));
        } catch (IOException ioException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ioException.getMessage());
        }
    }

}
