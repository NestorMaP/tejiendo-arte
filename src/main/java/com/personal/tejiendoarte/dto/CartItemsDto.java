package com.personal.tejiendoarte.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemsDto {

    private Long id;

    private Long productId;

    private String productName;

    private Long unitPrice;

    private Long quantity;

    private double lineTotalPrice;

    private Long orderId;

    private byte[] returnedImg;

    private Long userId;

}
