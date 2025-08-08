package com.personal.tejiendoarte.service.admin.adminproduct;

import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.entity.Category;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.repository.CategoryRepository;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.utils.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    public ProductDto addProduct(ProductDto productDto) throws IOException {

        Product newProduct = productMapper.mapToEntity(productDto);

        return productMapper.mapToDto(productRepository.save(newProduct));
    }

}
