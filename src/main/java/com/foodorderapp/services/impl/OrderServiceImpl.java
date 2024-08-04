package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Order;
import com.foodorderapp.models.entity.Product;
import com.foodorderapp.models.service.OrderServiceModel;
import com.foodorderapp.models.service.ProductServiceModel;
import com.foodorderapp.repositories.OrderRepository;
import com.foodorderapp.services.interfaces.OrderService;
import com.foodorderapp.services.interfaces.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderServiceModel> findAll() {
        List<Order> ordersDb = this.orderRepository.findAll();
        List<OrderServiceModel> ordersSM = new ArrayList<OrderServiceModel>();


        for (Order order : ordersDb) {
            List<ProductServiceModel> products = new ArrayList<>();
            order.getProducts().forEach(product -> {
                products.add(this.modelMapper.map(product, ProductServiceModel.class));
            });
            OrderServiceModel orderSM = new OrderServiceModel();
            orderSM.setProducts(products);
            orderSM.setActive(order.getIsActive());
            orderSM.setDate(order.getDate());
            orderSM.setId(order.getId());
            orderSM.setSum(order.getSum());
            orderSM.setUserData(order.getUserData());
            orderSM.setAddress(order.getAddress());

            ordersSM.add(orderSM);
        }

        return ordersSM;
    }

    @Override
    public List<OrderServiceModel> findAllByUser(String userData) {
        List<Order> ordersDb = this.orderRepository.findAllByUserData(userData);
        List<OrderServiceModel> ordersSM = new ArrayList<OrderServiceModel>();

        for (Order order : ordersDb) {
            List<ProductServiceModel> products = new ArrayList<>();
            order.getProducts().forEach(product -> {
                products.add(this.modelMapper.map(product, ProductServiceModel.class));
            });

            OrderServiceModel orderSM = new OrderServiceModel();
            orderSM.setProducts(products);
            orderSM.setActive(order.getIsActive());
            orderSM.setDate(order.getDate());
            orderSM.setId(order.getId());
            orderSM.setSum(order.getSum());
            orderSM.setUserData(order.getUserData());
            orderSM.setAddress(order.getAddress());

            ordersSM.add(orderSM);
        }

        return ordersSM;
    }

    @Override
    public Optional<OrderServiceModel> findById(String id) {
        var order = this.orderRepository.findById(id);
        List<ProductServiceModel> products = new ArrayList<>();

        OrderServiceModel orderSM = new OrderServiceModel();
        orderSM.setProducts(products);
        orderSM.setActive(order.get().getIsActive());
        orderSM.setDate(order.get().getDate());
        orderSM.setId(order.get().getId());
        orderSM.setSum(order.get().getSum());
        orderSM.setUserData(order.get().getUserData());
        orderSM.setAddress(order.get().getAddress());

        return Optional.of(orderSM);
    }

    @Override
    public OrderServiceModel addOrder(OrderServiceModel orderServiceModel) {
        try {
            Order newOrder = new Order();

            newOrder.setIsActive(orderServiceModel.getActive());
            newOrder.setAddress(orderServiceModel.getAddress());
            newOrder.setSum(orderServiceModel.getSum());
            newOrder.setDate(orderServiceModel.getDate());
            newOrder.setUserData(orderServiceModel.getUserData().split(", ")[1]);

            List<Product> products = new ArrayList<>();

            for (ProductServiceModel product : orderServiceModel.getProducts())
            {
                Product productModel = this.modelMapper.map(product, Product.class);
                products.add(productModel);
                newOrder.getProducts().add(productModel);
            }

            newOrder.setProducts(products);

            this.orderRepository.save(newOrder);
            orderServiceModel.setId(newOrder.getId());
            return orderServiceModel;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OrderServiceModel editOrder(OrderServiceModel orderServiceModel) {
        try {
            Order newOrder = orderRepository.findById(orderServiceModel.getId()).orElse(null);

            newOrder.setIsActive(orderServiceModel.getActive());

            this.orderRepository.save(newOrder);
            orderServiceModel.setId(newOrder.getId());
            return orderServiceModel;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OrderServiceModel> findByIsActive(Boolean isActive) {
        var allOrders =
                this.orderRepository
                        .findAll().stream().filter(Order::getIsActive)
                        .collect(Collectors.toList());

        var activeOrders = new ArrayList<OrderServiceModel>();

        for (Order order : allOrders) {
            activeOrders.add(this.modelMapper.map(order, OrderServiceModel.class));
        }

        return activeOrders;
    }
}
