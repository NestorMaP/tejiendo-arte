package com.personal.tejiendoarte.service.admin.adminproduct;

import com.personal.tejiendoarte.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    public List<ProductDto> getAllProducts() throws IOException;

    public ProductDto addProduct(ProductDto productDto) throws IOException;

}
