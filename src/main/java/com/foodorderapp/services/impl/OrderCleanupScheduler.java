package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Order;
import com.foodorderapp.repositories.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderCleanupScheduler {

    private OrderRepository orderRepository;

    @Scheduled(cron = "0 0 0 * * *") // Всеки ден в полунощ
    public void deleteInactiveOrdersFromYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Order> ordersToDelete = orderRepository.findByIsActiveFalseAndDate(yesterday);
        orderRepository.deleteAll(ordersToDelete);
    }
}
