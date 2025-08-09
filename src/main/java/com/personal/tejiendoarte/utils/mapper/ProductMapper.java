package com.personal.tejiendoarte.utils.mapper;

import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.entity.Category;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    public ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setByteImage(product.getByteImage());
        productDto.setCategoryId(product.getCategory().getId());

        return productDto;
    };

    public Product mapToEntity(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setByteImage(productDto.getImage().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IOException("Category not found"));
        product.setCategory(category);

        return product;
    }

}
