package com.personal.tejiendoarte.controller.customer;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.dto.PlaceOrderDto;
import com.personal.tejiendoarte.exceptions.ValidationException;
import com.personal.tejiendoarte.service.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUserId(userId));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addProductToCart(addProductInCartDto));
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        try {
            OrderDto orderDto = cartService.applyCoupon(userId, code);
            return ResponseEntity.status(HttpStatus.OK).body(orderDto);
        } catch (ValidationException validationException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
        }
    }

    @PostMapping("/cart/change-quantity")
    public ResponseEntity<OrderDto> modifyProductQuantity(
            @RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.changeProductQuantity(addProductInCartDto, addProductInCartDto.getDelta()));
    }

    @PostMapping("/cart/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }
}
