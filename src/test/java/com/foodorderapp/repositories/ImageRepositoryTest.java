package com.foodorderapp.repositories;

import com.foodorderapp.models.entity.Image;
import com.foodorderapp.services.impl.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageRepositoryTest extends BaseServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName() {
        Image image = new Image();
        image.setName("test-image");
        image.setType("jpg");
        image.setPicByte(new byte[]{1, 2, 3});

        when(imageRepository.findByName("test-image")).thenReturn(image);

        Image foundImage = imageRepository.findByName("test-image");

        assertNotNull(foundImage);
        assertEquals("test-image", foundImage.getName());
        assertEquals("jpg", foundImage.getType());
        assertArrayEquals(new byte[]{1, 2, 3}, foundImage.getPicByte());

        verify(imageRepository, times(1)).findByName("test-image");
    }
}