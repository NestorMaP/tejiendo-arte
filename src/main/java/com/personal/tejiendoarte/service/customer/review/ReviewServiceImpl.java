package com.personal.tejiendoarte.service.customer.review;

import com.personal.tejiendoarte.dto.OrderedProductsResponseDto;
import com.personal.tejiendoarte.dto.ProductDto;
import com.personal.tejiendoarte.dto.ReviewDto;
import com.personal.tejiendoarte.entity.Order;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.repository.OrderRepository;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.repository.UserRepository;
import com.personal.tejiendoarte.utils.mapper.ProductMapper;
import com.personal.tejiendoarte.utils.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    private final ProductMapper productMapper;
    private final ReviewMapper reviewMapper;

    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order " + orderId + " not Found"));

        List<ProductDto> productDtoList = order.getCartItems().stream()
                .map(cartItems -> {
                    ProductDto productDto = productMapper.mapToDto(cartItems.getProduct());
                    productDto.setQuantity(cartItems.getQuantity());
                    return productDto;
                })
                .toList();

        return OrderedProductsResponseDto.builder()
                .productDtoList(productDtoList)
                .orderAmount(order.getAmount())
                .build();
    }

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {

        return reviewRepository.save(reviewMapper.mapToEntity(reviewDto));
    }
}
