package com.personal.tejiendoarte.controller.customer;

import com.personal.tejiendoarte.dto.OrderedProductsResponseDto;
import com.personal.tejiendoarte.dto.ReviewDto;
import com.personal.tejiendoarte.service.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> getOrderedProductsDetailsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getOrderedProductsDetailsByOrderId(orderId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {

        try{
            ReviewDto review = reviewService.giveReview(reviewDto);
            return ResponseEntity.status(HttpStatus.OK).body(review);
        } catch (IOException ioException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ioException.getMessage());
        }
    }

}
