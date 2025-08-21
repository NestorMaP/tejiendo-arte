package com.personal.tejiendoarte.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ReviewDto {

    private Long id;

    private Long rating;

    private String description;

    private MultipartFile image;

    private byte[] byteImage;

    private Long userId;

    private String username;

    private Long productId;

}
