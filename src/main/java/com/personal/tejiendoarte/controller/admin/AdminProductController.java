package com.personal.tejiendoarte.controller.admin;

import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.service.admin.adminproduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto requestProductDto) throws IOException {
        ProductDto responseProductDto = adminProductService.addProduct(requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseProductDto);
    }

}
