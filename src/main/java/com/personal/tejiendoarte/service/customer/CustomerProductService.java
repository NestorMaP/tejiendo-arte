package com.personal.tejiendoarte.service.customer;

import com.personal.tejiendoarte.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface CustomerProductService {

    public List<ProductDto> getAllProducts() throws IOException;

    public List<ProductDto> getAllProductsByName(String productName);
}
