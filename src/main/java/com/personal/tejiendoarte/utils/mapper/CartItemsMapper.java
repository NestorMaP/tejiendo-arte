package com.personal.tejiendoarte.utils.mapper;

import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.entity.CartItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemsMapper {

    public CartItemsDto mapToDto(CartItems cartItem) {

        return CartItemsDto.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .unitPrice(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .lineTotalPrice(cartItem.getPrice() * cartItem.getQuantity())
                .orderId(cartItem.getOrder().getId())
                .returnedImg(cartItem.getProduct().getByteImage())
                .userId(cartItem.getUser().getId())
                .build();
    }

}
