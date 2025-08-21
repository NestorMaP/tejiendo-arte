package com.personal.tejiendoarte.utils.mapper;

import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.entity.Review;
import com.personal.tejiendoarte.dto.ReviewDto;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.repository.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Data
@Component
public class ReviewMapper {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Review mapToEntity(ReviewDto reviewDto) throws IOException {

        Product product = productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product " + reviewDto.getProductId() + " not Found")
                );
        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User " + reviewDto.getUserId() + " not Found")
                );

        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        review.setUser(user);
        review.setProduct(product);
        review.setImage(reviewDto.getImage().getBytes());

        return review;
    }

    public ReviewDto mapToDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .rating(review.getRating())
                .description(review.getDescription())
                .byteImage(review.getImage())
                .userId(review.getUser().getId())
                .username(review.getUser().getFirst_name())
                .productId(review.getProduct().getId())
                .build();
    }
}
