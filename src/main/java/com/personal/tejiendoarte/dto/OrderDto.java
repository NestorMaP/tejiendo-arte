package com.personal.tejiendoarte.dto;

import com.personal.tejiendoarte.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderDto {

    private Long id;

    private String description;

    private Date date;

    private Long amount;

    private String address;

    private String payment;

    private OrderStatus status;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    private String userName;

    private List<CartItemsDto> cartItems;

}
