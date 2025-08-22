package com.personal.tejiendoarte.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishlistDto {

    private Long id;

    private Long userId;

    private Long productId;

    private String productName;

    private String productDescription;

    private byte[] byteImage;

    private Long price;
}
