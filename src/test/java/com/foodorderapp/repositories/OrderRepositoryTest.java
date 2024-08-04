package com.foodorderapp.repositories;

import com.foodorderapp.models.entity.Order;
import com.foodorderapp.services.impl.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderRepositoryTest extends BaseServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByUserData() {
        String userData = "user123";
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setUserData(userData);
        order1.setDate(LocalDate.now().toString());
        order1.setSum(BigDecimal.valueOf(100));
        order1.setAddress("123 Street");
        order1.setIsActive(true);
        orders.add(order1);

        when(orderRepository.findAllByUserData(userData)).thenReturn(orders);

        List<Order> foundOrders = orderRepository.findAllByUserData(userData);

        assertNotNull(foundOrders);
        assertEquals(1, foundOrders.size());
        assertEquals(userData, foundOrders.get(0).getUserData());

        verify(orderRepository, times(1)).findAllByUserData(userData);
    }

    @Test
    void findByIsActiveFalseAndDate() {
        LocalDate date = LocalDate.now();
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setUserData("user123");
        order1.setDate(date.toString());
        order1.setSum(BigDecimal.valueOf(100));
        order1.setAddress("123 Street");
        order1.setIsActive(false);
        orders.add(order1);

        when(orderRepository.findByIsActiveFalseAndDate(date)).thenReturn(orders);

        List<Order> foundOrders = orderRepository.findByIsActiveFalseAndDate(date);

        assertNotNull(foundOrders);
        assertEquals(1, foundOrders.size());
        assertFalse(foundOrders.get(0).getIsActive());
        assertEquals(date.toString(), foundOrders.get(0).getDate());

        verify(orderRepository, times(1)).findByIsActiveFalseAndDate(date);
    }
}