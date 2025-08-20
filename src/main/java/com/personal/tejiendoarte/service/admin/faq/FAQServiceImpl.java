package com.personal.tejiendoarte.service.admin.faq;

import com.personal.tejiendoarte.dto.FAQDto;
import com.personal.tejiendoarte.entity.FAQ;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.repository.FAQRepository;
import com.personal.tejiendoarte.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    private final ProductRepository productRepository;

    public FAQDto postFAQ(Long productId, FAQDto faqDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isEmpty()) return null;

        FAQ faq = new FAQ();


    }

}
