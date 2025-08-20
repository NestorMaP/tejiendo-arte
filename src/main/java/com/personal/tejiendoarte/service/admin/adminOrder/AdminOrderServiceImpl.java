package com.personal.tejiendoarte.service.admin.adminOrder;

import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.entity.Order;
import com.personal.tejiendoarte.enums.OrderStatus;
import com.personal.tejiendoarte.repository.OrderRepository;
import com.personal.tejiendoarte.utils.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {

        List<Order> orderList = orderRepository.findAllByStatusIn(List.of(
                OrderStatus.PLACED,
                OrderStatus.SHIPPED,
                OrderStatus.DELIVERED));

        return orderList.stream().map(orderMapper::mapToDto).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if(optionalOrder.isEmpty()) return null;

        Order order = optionalOrder.get();

        if(status.equalsIgnoreCase(OrderStatus.SHIPPED.name())) {
            order.setStatus(OrderStatus.SHIPPED);
        } else if(status.equalsIgnoreCase(OrderStatus.DELIVERED.name())) {
            order.setStatus(OrderStatus.DELIVERED);
        }
        return orderMapper.mapToDto(orderRepository.save(order));
    }

}
