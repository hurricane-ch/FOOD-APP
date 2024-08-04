package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Order;
import com.foodorderapp.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class OrderCleanupSchedulerTest extends BaseServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderCleanupScheduler orderCleanupScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteInactiveOrdersFromYesterday() {
        String yesterday = LocalDate.now().minusDays(1).toString(); // Assuming date is stored as "yyyy-MM-dd"
        List<Order> mockOrdersToDelete = createMockOrders(yesterday);

        when(orderRepository.findByIsActiveFalseAndDate(any()))
                .thenReturn(mockOrdersToDelete);

        orderCleanupScheduler.deleteInactiveOrdersFromYesterday();

        verify(orderRepository, times(1)).findByIsActiveFalseAndDate(any());
        verify(orderRepository, times(1)).deleteAll(mockOrdersToDelete);
    }

    private List<Order> createMockOrders(String date) {
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder("1", false, date));
        orders.add(createOrder("2", false, date));
        return orders;
    }

    private Order createOrder(String id, boolean isActive, String date) {
        Order order = new Order();
        order.setId(id);
        order.setIsActive(isActive);
        order.setDate(date);
        return order;
    }
}