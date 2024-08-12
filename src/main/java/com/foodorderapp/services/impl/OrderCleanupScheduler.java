package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Order;
import com.foodorderapp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCleanupScheduler {

    private final OrderRepository orderRepository;

//    @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(cron = "1 0 22 * * *")
    protected void deleteInactiveOrders() {
        List<Order> ordersToDelete = orderRepository.findAllByIsActiveFalse();
        orderRepository.deleteAll(ordersToDelete);
    }
}
