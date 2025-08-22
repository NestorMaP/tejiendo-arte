package com.personal.tejiendoarte.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class AnalyticsResponseDto {

    private Long placed;

    private Long shipped;

    private Long delivered;

    private Long currentMonthOrders;

    private Long previousMonthOrders;

    private BigDecimal currentMonthEarnings;

    private BigDecimal previousMonthEarnings;

}
