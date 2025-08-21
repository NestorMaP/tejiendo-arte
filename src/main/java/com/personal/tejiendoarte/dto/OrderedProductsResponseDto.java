package com.personal.tejiendoarte.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderedProductsResponseDto {

    private List<ProductDto> productDtoList;

    private Long orderAmount;

}
