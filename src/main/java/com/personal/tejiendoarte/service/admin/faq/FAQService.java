package com.personal.tejiendoarte.service.admin.faq;

import com.personal.tejiendoarte.dto.FAQDto;

public interface FAQService {

    public FAQDto postFAQ(Long productId, FAQDto faqDto);

}
