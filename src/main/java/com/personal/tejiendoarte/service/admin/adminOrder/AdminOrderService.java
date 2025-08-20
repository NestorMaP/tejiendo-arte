package com.personal.tejiendoarte.service.admin.adminOrder;

import com.personal.tejiendoarte.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    public List<OrderDto> getAllOrders();

    public OrderDto changeOrderStatus(Long orderId, String status);

}
