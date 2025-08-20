package com.personal.tejiendoarte.utils.mapper;

import com.personal.tejiendoarte.dto.FAQDto;
import com.personal.tejiendoarte.entity.FAQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FAQMapper {

    public FAQDto mapToDto(FAQ faq) {
        return FAQDto.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .productId(faq.getProduct().getId())
                .build();
    }

}
