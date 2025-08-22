package com.personal.tejiendoarte.repository;

import com.personal.tejiendoarte.entity.Order;
import com.personal.tejiendoarte.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndStatus(Long userId, OrderStatus status);

    List<Order> findAllByStatusIn(List<OrderStatus> orderStatusList);

    List<Order> findAllByUserIdAndStatusIn(Long userId, List<OrderStatus> orderStatusList);

    Optional<Order> findByTrackingId(UUID trackingId);

    List<Order> findByDateBetweenAndStatus(Date startOfMonth, Date endOfMonth, OrderStatus status);
}
