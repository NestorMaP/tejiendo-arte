package com.personal.tejiendoarte.repository;

import com.personal.tejiendoarte.entity.Order;
import com.personal.tejiendoarte.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndStatus(Long userId, OrderStatus status);
}
