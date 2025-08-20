package com.personal.tejiendoarte.controller.admin;

import com.personal.tejiendoarte.dto.FAQDto;
import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.service.admin.adminproduct.AdminProductService;
import com.personal.tejiendoarte.service.admin.faq.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    private final FAQService faqService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(adminProductService.getAllProducts());
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String productName) {
        return ResponseEntity.status(HttpStatus.OK).body(adminProductService.getAllProductsByName(productName));
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto requestProductDto) throws IOException {
        ProductDto responseProductDto = adminProductService.addProduct(requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseProductDto);
    }

    // TODO: Improve the following method
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long productId) {
        boolean deleted = adminProductService.deleteProduct(productId);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Product deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Product not found"));

    }

    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFAQ(@PathVariable Long productId, @RequestBody FAQDto faqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(faqService.postFAQ(productId, faqDto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminProductService.getProductById(productId));
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long productId,
            @ModelAttribute ProductDto productDto)
            throws IOException
    {
        return ResponseEntity.status(HttpStatus.OK).body(adminProductService.updateProduct(productId, productDto));
    }
}
