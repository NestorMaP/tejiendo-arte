package com.personal.tejiendoarte.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FAQDto {

    private Long id;

    private String question;

    private String answer;

    private Long productId;

}
