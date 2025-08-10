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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts() throws IOException {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::mapToDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByName(String productName) {
        List<Product> products = productRepository.findAllByNameContaining(productName);
        return products.stream().map(productMapper::mapToDto).collect(Collectors.toList());
    }

    public ProductDto addProduct(ProductDto productDto) throws IOException {

        Product newProduct = productMapper.mapToEntity(productDto);
        return productMapper.mapToDto(productRepository.save(newProduct));
    }



}
