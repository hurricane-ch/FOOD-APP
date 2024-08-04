package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Image;
import com.foodorderapp.models.entity.Product;
import com.foodorderapp.models.service.ProductServiceModel;
import com.foodorderapp.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductServiceImplTest extends BaseServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductServiceModel productServiceModel;
    private Image image;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId("1");
        product.setName("Product1");
        product.setType("Type1");
        product.setAvailable(true);

        productServiceModel = new ProductServiceModel();
        productServiceModel.setId("1");
        productServiceModel.setName("Product1");
        productServiceModel.setType("Type1");
    }

    @Test
    public void findByName() {
        when(productRepository.findByName(anyString())).thenReturn(product);
        when(modelMapper.map(any(Product.class), any())).thenReturn(productServiceModel);

        ProductServiceModel result = productService.findByName("Product1");
        assertNotNull(result);
        assertEquals("Product1", result.getName());

        verify(productRepository, times(1)).findByName(anyString());
        verify(modelMapper, times(1)).map(any(Product.class), any());
    }

    @Test
    void findByType() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(any(Product.class), any())).thenReturn(productServiceModel);

        List<ProductServiceModel> result = productService.findByType("Type1");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Type1", result.get(0).getType());

        verify(productRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(any(Product.class), any());
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(any(Product.class), any())).thenReturn(productServiceModel);

        List<ProductServiceModel> result = productService.findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(productRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(any(Product.class), any());
    }

    @Test
    void findById() {
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        when(modelMapper.map(any(Product.class), any())).thenReturn(productServiceModel);

        ProductServiceModel result = productService.findById("1");

        verify(productRepository, times(1)).findById(anyString());
        verify(modelMapper, times(1)).map(any(Product.class), any());
    }

    @Test
    void addProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(any(ProductServiceModel.class), any())).thenReturn(product);
        when(modelMapper.map(any(Product.class), any())).thenReturn(productServiceModel);

        ProductServiceModel result = productService.addProduct(productServiceModel);
        assertNotNull(result);
        assertEquals("Product1", result.getName());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(modelMapper, times(1)).map(any(ProductServiceModel.class), any());
        verify(modelMapper, times(1)).map(any(Product.class), any());
    }

    @Test
    void editProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(any(ProductServiceModel.class), any())).thenReturn(product);

        ProductServiceModel result = productService.editProduct(productServiceModel);
        assertNotNull(result);
        assertEquals("Product1", result.getName());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(modelMapper, times(1)).map(any(ProductServiceModel.class), any());
    }

    @Test
    void deleteById() {
    }
}