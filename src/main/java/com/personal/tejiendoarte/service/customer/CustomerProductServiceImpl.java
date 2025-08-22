package com.personal.tejiendoarte.service.customer;

import com.personal.tejiendoarte.dto.ProductDetailDto;
import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.utils.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts() throws IOException {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::mapToDto).collect(Collectors.toList());
    }

    public List<ProductDto> searchProductByTitle(String productName) {
        List<Product> products = productRepository.findAllByNameContaining(productName);
        return products.stream().map(productMapper::mapToDto).collect(Collectors.toList());
    }

    public ProductDetailDto getProductDetailById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product " + productId + " not Found"));


    }
}
