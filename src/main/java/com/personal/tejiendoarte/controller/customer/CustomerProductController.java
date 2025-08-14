package com.personal.tejiendoarte.controller.customer;

import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.service.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(customerProductService.getAllProducts());
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String productName) {
        return ResponseEntity.status(HttpStatus.OK).body(customerProductService.searchProductByTitle(productName));
    }
}
