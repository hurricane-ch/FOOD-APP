package com.foodorderapp.repositories;

import com.foodorderapp.models.entity.Product;
import com.foodorderapp.services.impl.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRepositoryTest extends BaseServiceTest {

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setContent("Description 1");
        product1.setType("Type 1");
        product1.setVolume(500);
        product1.setPrice(10.0);
        product1.setAvailable(true);
        products.add(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setContent("Description 2");
        product2.setType("Type 2");
        product2.setVolume(1000);
        product2.setPrice(20.0);
        product2.setAvailable(true);
        products.add(product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productRepository.findAll();

        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
        assertTrue(foundProducts.stream().allMatch(Product::getAvailable));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findByName() {
        String productName = "Product 1";
        Product product = new Product();
        product.setName(productName);
        product.setContent("Description 1");
        product.setType("Type 1");
        product.setVolume(500);
        product.setPrice(10.0);
        product.setAvailable(true);

        when(productRepository.findByName(productName)).thenReturn(product);

        Product foundProduct = productRepository.findByName(productName);

        assertNotNull(foundProduct);
        assertEquals(productName, foundProduct.getName());
        assertEquals("Description 1", foundProduct.getContent());
        assertEquals("Type 1", foundProduct.getType());
        assertEquals(500, foundProduct.getVolume());
        assertEquals(10.0, foundProduct.getPrice());
        assertTrue(foundProduct.getAvailable());

        verify(productRepository, times(1)).findByName(productName);
    }
}