package com.personal.tejiendoarte.utils.mapper;

import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final CartItemsMapper cartItemsMapper;

    public OrderDto mapToDto(Order order) {

        return OrderDto.builder()
                .id(order.getId())
                .description(order.getDescription())
                .date(order.getDate())
                .amount(order.getAmount())
                .address(order.getAddress())
                .payment(order.getPayment())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .discount(order.getDiscount())
                .trackingId(order.getTrackingId())
                .userName(order.getUser().getFirst_name())
                .cartItems(order.getCartItems().stream().map(cartItemsMapper::mapToDto).collect(Collectors.toList()))
                .build();

    }

}
