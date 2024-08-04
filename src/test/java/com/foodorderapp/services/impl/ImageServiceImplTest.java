package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Image;
import com.foodorderapp.models.service.ImageServiceModel;
import com.foodorderapp.repositories.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageServiceImplTest extends BaseServiceTest {

    private ImageRepository imageRepository;
    private ModelMapper modelMapper;
    private ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
        imageRepository = mock(ImageRepository.class);
        modelMapper = new ModelMapper();
        imageService = new ImageServiceImpl(imageRepository, modelMapper);
    }

    @Test
    void upload_newImage() {
        ImageServiceModel imageServiceModel = new ImageServiceModel();
        imageServiceModel.setName("testImage");
        imageServiceModel.setType("png");
        imageServiceModel.setPicByte(new byte[]{});

        when(imageRepository.findByName("testImage")).thenReturn(null);
        when(imageRepository.save(any(Image.class))).thenAnswer(i -> i.getArguments()[0]);

        ImageServiceModel result = imageService.upload(imageServiceModel);

        assertNotNull(result);
        assertEquals("testImage", result.getName());
        verify(imageRepository, times(1)).save(any(Image.class));
    }

    @Test
    void upload_existingImage() {
        ImageServiceModel imageServiceModel = new ImageServiceModel();
        imageServiceModel.setName("testImage");
        imageServiceModel.setType("png");
        imageServiceModel.setPicByte(new byte[]{});

        Image existingImage = new Image();
        existingImage.setName("testImage");

        when(imageRepository.findByName("testImage")).thenReturn(existingImage);
        when(imageRepository.saveAndFlush(any(Image.class))).thenAnswer(i -> i.getArguments()[0]);

        ImageServiceModel result = imageService.upload(imageServiceModel);

        assertNotNull(result);
        assertEquals("testImage", result.getName());
        verify(imageRepository, times(1)).saveAndFlush(any(Image.class));
    }

    @Test
    void edit() {
        ImageServiceModel imageServiceModel = new ImageServiceModel();
        imageServiceModel.setName("testImage");
        imageServiceModel.setType("png");
        imageServiceModel.setPicByte(new byte[]{});

        Image existingImage = new Image();
        existingImage.setName("testImage");

        when(imageRepository.findByName("testImage")).thenReturn(existingImage);
        when(imageRepository.saveAndFlush(any(Image.class))).thenAnswer(i -> i.getArguments()[0]);

        ImageServiceModel result = imageService.edit(imageServiceModel);

        assertNotNull(result);
        assertEquals("testImage", result.getName());
        verify(imageRepository, times(1)).saveAndFlush(any(Image.class));
    }

    @Test
    void delete() {
        Image existingImage = new Image();
        existingImage.setName("testImage");

        when(imageRepository.findByName("testImage")).thenReturn(existingImage);
        doNothing().when(imageRepository).delete(any(Image.class));

        imageService.delete("testImage");

        verify(imageRepository, times(1)).delete(any(Image.class));
    }

    @Test
    void findByName() {
        Image existingImage = new Image();
        existingImage.setName("testImage");

        when(imageRepository.findByName("testImage")).thenReturn(existingImage);

        ImageServiceModel result = imageService.findByName("testImage");

        assertNotNull(result);
        assertEquals("testImage", result.getName());
        verify(imageRepository, times(1)).findByName("testImage");
    }
}