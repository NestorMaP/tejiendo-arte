package com.personal.tejiendoarte.service.admin.adminproduct;

import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.entity.Category;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.utils.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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

    //TODO: Improve the following method
    public boolean deleteProduct(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(productMapper::mapToDto).orElse(null);

    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product productToUpdate = optionalProduct.get();
            Product updatedProduct = updateProductInfo(productToUpdate, optionalCategory.get(), productDto);
            return  productMapper.mapToDto(productRepository.save(updatedProduct));
        } else return null;
    }

    private Product updateProductInfo(Product product, Category category, ProductDto productDto) throws IOException {
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        if(productDto.getImage() != null) {
            product.setByteImage(productDto.getImage().getBytes());
        }
        return product;
    }

}
