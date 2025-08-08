package com.personal.tejiendoarte.controller.admin;

import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.service.admin.adminproduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(adminProductService.getAllProducts());
    }

    @GetMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto requestProductDto) throws IOException {
        ProductDto responseProductDto = adminProductService.addProduct(requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseProductDto);
    }

}
