package com.personal.tejiendoarte.service.admin.adminproduct;

import com.personal.tejiendoarte.dto.ProductDto;

import java.io.IOException;

public interface AdminProductService {

    public ProductDto addProduct(ProductDto productDto) throws IOException;

}
