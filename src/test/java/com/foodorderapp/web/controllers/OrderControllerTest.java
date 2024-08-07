package com.foodorderapp.web.controllers;

import com.foodorderapp.models.binding.order.OrderAddBindingModel;
import com.foodorderapp.models.binding.order.OrderEditBindingModel;
import com.foodorderapp.models.service.OrderServiceModel;
import com.foodorderapp.models.view.OrderViewModel;
import com.foodorderapp.services.impl.BaseServiceTest;
import com.foodorderapp.services.interfaces.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderControllerTest extends BaseServiceTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAll() {
        List<OrderServiceModel> orderServiceModels = new ArrayList<>();
        List<OrderViewModel> orderViewModels = new ArrayList<>();

        when(orderService.findAll()).thenReturn(orderServiceModels);
        when(modelMapper.map(any(OrderServiceModel.class), any())).thenReturn(new OrderViewModel());

        ResponseEntity<?> response = orderController.getAll();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getAllByUser() {
        String userId = "userId";
        List<OrderServiceModel> orderServiceModels = new ArrayList<>();
        List<OrderViewModel> orderViewModels = new ArrayList<>();

        when(orderService.findAllByUser(userId)).thenReturn(orderServiceModels);
        when(modelMapper.map(any(OrderServiceModel.class), any())).thenReturn(new OrderViewModel());

        ResponseEntity<?> response = orderController.getAllByUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

//    @Test
//    void addOrder() {
//        OrderAddBindingModel orderAddBindingModel = new OrderAddBindingModel();
//        orderAddBindingModel.setIsActive(true);
//        OrderServiceModel orderServiceModel = new OrderServiceModel();
//        OrderViewModel orderViewModel = new OrderViewModel();
//
//        when(modelMapper.map(orderAddBindingModel, OrderServiceModel.class)).thenReturn(orderServiceModel);
//        when(orderService.addOrder(orderServiceModel)).thenReturn(orderServiceModel);
//        when(modelMapper.map(orderServiceModel, OrderViewModel.class)).thenReturn(orderViewModel);
//
//        ResponseEntity<?> response = orderController.addOrder(orderAddBindingModel);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(orderViewModel, response.getBody());
//    }

//    @Test
//    void getOrder() {
//        String id = "id";
//        OrderServiceModel orderServiceModel = new OrderServiceModel();
//        OrderViewModel orderViewModel = new OrderViewModel();
//
//        when(orderService.findById(id)).thenReturn(Optional.of(orderServiceModel));
//        when(modelMapper.map(orderServiceModel, OrderViewModel.class)).thenReturn(orderViewModel);
//
//        ResponseEntity<?> response = orderController.getOrder(id);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(orderViewModel, response.getBody());
//    }

//    @Test
//    void editOrder() {
//        OrderEditBindingModel orderEditBindingModel = new OrderEditBindingModel();
//        orderEditBindingModel.setId("id");
//        orderEditBindingModel.setActive(false);
//        OrderServiceModel orderServiceModel = new OrderServiceModel();
//
//        when(orderService.findById(orderEditBindingModel.getId())).thenReturn(Optional.of(orderServiceModel));
//        when(modelMapper.map(any(OrderServiceModel.class), any())).thenReturn(orderServiceModel);
//        when(orderService.editOrder(orderServiceModel)).thenReturn(orderServiceModel);
//
//        ResponseEntity<?> response = orderController.editOrder(orderEditBindingModel);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(orderServiceModel, response.getBody());
//    }
}