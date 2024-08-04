package com.foodorderapp.web.controllers;

import com.foodorderapp.models.binding.product.ProductAddBindingModel;
import com.foodorderapp.models.service.ProductServiceModel;
import com.foodorderapp.models.view.ProductViewModel;
import com.foodorderapp.services.interfaces.ProductService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductControllerTest extends BaseControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts() {
        List<ProductServiceModel> productServiceModels = new ArrayList<>();
        ProductServiceModel productServiceModel = new ProductServiceModel();
        ProductViewModel productViewModel = new ProductViewModel();
        productServiceModels.add(productServiceModel);

        when(productService.findAll()).thenReturn(productServiceModels);
        when(modelMapper.map(productServiceModel, ProductViewModel.class)).thenReturn(productViewModel);

        ResponseEntity<?> response = productController.getAllProducts();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("No products...", response.getBody());
    }

    @Test
    void addProduct() {
        ProductAddBindingModel bindingModel = new ProductAddBindingModel();
        ProductServiceModel serviceModel = new ProductServiceModel();
        ProductServiceModel addedProduct = new ProductServiceModel();
        ProductViewModel viewModel = new ProductViewModel();

        when(modelMapper.map(bindingModel, ProductServiceModel.class)).thenReturn(serviceModel);
        when(productService.addProduct(serviceModel)).thenReturn(addedProduct);
        when(modelMapper.map(addedProduct, ProductViewModel.class)).thenReturn(viewModel);

        ResponseEntity<?> response = productController.addProduct(bindingModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getProduct() {
        String productId = "1";
        ProductServiceModel serviceModel = new ProductServiceModel();
        ProductViewModel viewModel = new ProductViewModel();

        when(productService.findById(productId)).thenReturn(serviceModel);
        when(modelMapper.map(serviceModel, ProductViewModel.class)).thenReturn(viewModel);

        ResponseEntity<ProductViewModel> response = productController.getProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteProduct() {
        String productId = "1";

        ResponseEntity<HttpStatus> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}