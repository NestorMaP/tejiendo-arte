package com.personal.tejiendoarte.utils.mapper;

import com.personal.tejiendoarte.dto.ProductDetailDto;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.entity.Review;
import com.personal.tejiendoarte.repository.FAQRepository;
import com.personal.tejiendoarte.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ProductDetailMapper {

    private final ReviewRepository reviewRepository;
    private final FAQRepository faqRepository;

    public ProductDetailDto mapToDto(Product product) {
        Review review = reviewRepository.findById(product.getId())
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product " + product.getId() + " not Found"));
    }

}
