package com.personal.tejiendoarte.service.admin.adminOrder;

import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    public List<OrderDto> getAllOrders() {

    }

}
