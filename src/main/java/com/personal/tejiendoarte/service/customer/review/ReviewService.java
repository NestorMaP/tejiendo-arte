package com.personal.tejiendoarte.service.customer.review;

import com.personal.tejiendoarte.dto.OrderedProductsResponseDto;

public interface ReviewService {

    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

}
