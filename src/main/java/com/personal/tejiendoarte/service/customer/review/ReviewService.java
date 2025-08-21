package com.personal.tejiendoarte.service.customer.review;

import com.personal.tejiendoarte.dto.OrderedProductsResponseDto;
import com.personal.tejiendoarte.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {

    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException;

}
