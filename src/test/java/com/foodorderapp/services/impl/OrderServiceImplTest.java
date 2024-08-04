package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Order;
import com.foodorderapp.models.entity.Product;
import com.foodorderapp.models.service.OrderServiceModel;
import com.foodorderapp.models.service.ProductServiceModel;
import com.foodorderapp.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

class OrderServiceImplTest extends BaseServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(new Order(/* add constructor parameters if applicable */));
        // Add more mock orders as needed

        when(orderRepository.findAll()).thenReturn(mockOrders);

        // Mock mapping from Order to OrderServiceModel
        when(modelMapper.map(any(Order.class), eq(OrderServiceModel.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            orderServiceModel.setId(order.getId());
            // Map other fields as needed
            return orderServiceModel;
        });

        // Test
        List<OrderServiceModel> result = orderService.findAll();

        // Verification
        assertNotNull(result);
        assertEquals(mockOrders.size(), result.size());
        // Add assertions to verify each field mapping
    }

    @Test
    void findAllByUser() {
        String userData = "user3@example.com, John Doe";

        // Mock data
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(createMockOrder("1", userData)); // Example: Assuming you have a method to create mock orders
        // Add more mock orders as needed

        when(orderRepository.findAllByUserData(userData)).thenReturn(mockOrders);

        // Mock mapping from Order to OrderServiceModel
        when(modelMapper.map(any(Order.class), eq(OrderServiceModel.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            orderServiceModel.setId(order.getId());
            orderServiceModel.setUserData(order.getUserData());
            orderServiceModel.setDate(order.getDate());
            orderServiceModel.setSum(order.getSum());
            orderServiceModel.setAddress(order.getAddress());
            orderServiceModel.setActive(order.getIsActive());
            // Map other fields as needed
            return orderServiceModel;
        });

        // Test
        List<OrderServiceModel> result = orderService.findAllByUser(userData);

        // Verification
        assertNotNull(result);
        assertEquals(mockOrders.size(), result.size());

        // Verify each field mapping
        for (int i = 0; i < mockOrders.size(); i++) {
            Order mockOrder = mockOrders.get(i);
            OrderServiceModel mappedOrder = result.get(i);

            assertEquals(mockOrder.getId(), mappedOrder.getId());
            assertEquals(mockOrder.getUserData(), mappedOrder.getUserData());
            assertEquals(mockOrder.getDate(), mappedOrder.getDate());
            assertEquals(mockOrder.getSum(), mappedOrder.getSum());
            assertEquals(mockOrder.getAddress(), mappedOrder.getAddress());
            assertEquals(mockOrder.getIsActive(), mappedOrder.getActive());
            // Add assertions for other fields if needed
        }
    }

    // Utility method to create a mock Order instance
    private Order createMockOrder(String id, String userData) {
        Order order = new Order();
        order.setId(id);
        order.setUserData(userData);
        order.setDate("2024-06-20"); // Example date
        order.setSum(BigDecimal.valueOf(200)); // Example sum
        order.setAddress("789 Road"); // Example address
        order.setIsActive(true); // Example active status
        // Add more fields as needed
        return order;
    }

    @Test
    void findById() {
        String orderId = "1";
        Order mockOrder = createMockOrder(orderId, "user3@example.com, John Doe");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Mock mapping from Order to OrderServiceModel
        when(modelMapper.map(any(Order.class), eq(OrderServiceModel.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            orderServiceModel.setId(order.getId());
            orderServiceModel.setUserData(order.getUserData());
            orderServiceModel.setDate(order.getDate());
            orderServiceModel.setSum(order.getSum());
            orderServiceModel.setAddress(order.getAddress());
            orderServiceModel.setActive(order.getIsActive());
            // Map other fields as needed
            return orderServiceModel;
        });

        // Test
        Optional<OrderServiceModel> result = orderService.findById(orderId);

        // Verification
        OrderServiceModel foundOrder = result.get();
        assertEquals(mockOrder.getId(), foundOrder.getId());
        assertEquals(mockOrder.getUserData(), foundOrder.getUserData());
        assertEquals(mockOrder.getDate(), foundOrder.getDate());
        assertEquals(mockOrder.getSum(), foundOrder.getSum());
        assertEquals(mockOrder.getAddress(), foundOrder.getAddress());
        assertEquals(mockOrder.getIsActive(), foundOrder.getActive());
        // Add assertions for other fields if needed
    }

    @Test
    void addOrder() {
        // Mock data
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setActive(true);
        orderServiceModel.setDate(LocalDate.now().toString());
        orderServiceModel.setSum(BigDecimal.valueOf(200));
        orderServiceModel.setUserData("user3@example.com, John Doe");
        orderServiceModel.setAddress("789 Road");

        ProductServiceModel product1 = new ProductServiceModel();
        product1.setId("p1");
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(200));

        ProductServiceModel product2 = new ProductServiceModel();
        product2.setId("p2");
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(200));

        List<ProductServiceModel> products = List.of(product1, product2);
        orderServiceModel.setProducts(products);

        // Mock mapping from ProductServiceModel to Product
        when(modelMapper.map(any(ProductServiceModel.class), eq(Product.class))).thenAnswer(invocation -> {
            ProductServiceModel productServiceModel = invocation.getArgument(0);
            Product product = new Product();
            product.setId(productServiceModel.getId());
            product.setName(productServiceModel.getName());
            product.setPrice(productServiceModel.getPrice().doubleValue());
            // Map other fields as needed
            return product;
        });

        // Test
        OrderServiceModel result = orderService.addOrder(orderServiceModel);

        // Verification
        assertNotNull(result);
        assertEquals(orderServiceModel.getActive(), result.getActive());
        assertEquals(orderServiceModel.getDate(), result.getDate());
        assertEquals(orderServiceModel.getSum(), result.getSum());
        assertEquals(orderServiceModel.getUserData(), result.getUserData());
        assertEquals(orderServiceModel.getAddress(), result.getAddress());
        assertEquals(orderServiceModel.getProducts().size(), result.getProducts().size());
        // Verify other fields and mappings
    }

    @Test
    void editOrder() {
        String orderId = "1";
        Order mockOrder = createMockOrder(orderId, "user3@example.com, John Doe");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderServiceModel updatedOrderServiceModel = new OrderServiceModel();
        updatedOrderServiceModel.setId(orderId);
        updatedOrderServiceModel.setActive(false); // Updated active status

        // Test
        OrderServiceModel result = orderService.editOrder(updatedOrderServiceModel);

        // Verification
        assertNotNull(result);
        assertEquals(updatedOrderServiceModel.getId(), result.getId());
        assertEquals(updatedOrderServiceModel.getActive(), result.getActive());
        // Add assertions for other fields if needed
    }

    @Test
    void findByIsActive() {
        Order activeOrder = createMockOrder("1", "user1@example.com, Jane Doe");
        activeOrder.setIsActive(true);

        Order inactiveOrder = createMockOrder("2", "user2@example.com, Joe Bloggs");
        inactiveOrder.setIsActive(false);

        List<Order> mockOrders = List.of(activeOrder, inactiveOrder);

        when(orderRepository.findAll()).thenReturn(mockOrders);

        // Mock mapping from Order to OrderServiceModel
        when(modelMapper.map(any(Order.class), eq(OrderServiceModel.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            orderServiceModel.setId(order.getId());
            orderServiceModel.setUserData(order.getUserData());
            orderServiceModel.setDate(order.getDate());
            orderServiceModel.setSum(order.getSum());
            orderServiceModel.setAddress(order.getAddress());
            orderServiceModel.setActive(order.getIsActive());
            // Map other fields as needed
            return orderServiceModel;
        });

        // Test
        List<OrderServiceModel> result = orderService.findByIsActive(true);

        // Verification
        assertNotNull(result);
        assertEquals(1, result.size()); // Assuming only one active order in the mock data
        assertEquals(activeOrder.getId(), result.get(0).getId());
        assertEquals(activeOrder.getUserData(), result.get(0).getUserData());
        assertEquals(activeOrder.getDate(), result.get(0).getDate());
        assertEquals(activeOrder.getSum(), result.get(0).getSum());
        assertEquals(activeOrder.getAddress(), result.get(0).getAddress());
        assertEquals(activeOrder.getIsActive(), result.get(0).getActive());
        // Add assertions for other fields if needed
    }

}