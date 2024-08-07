package com.foodorderapp.web.controllers;

import com.foodorderapp.models.binding.image.ImageBindingModel;
import com.foodorderapp.models.entity.Image;
import com.foodorderapp.models.service.ImageServiceModel;
import com.foodorderapp.models.view.ImageViewModel;
import com.foodorderapp.repositories.ImageRepository;
import com.foodorderapp.services.impl.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ImageControllerTest extends BaseControllerTest {

    @Mock
    private ImageController imageController;
    @Mock
    private ImageServiceImpl imageService;
    @Mock
    private ModelMapper modelMapper;



    @BeforeEach
    void setUp() {
        imageService = mock(ImageServiceImpl.class);
        modelMapper = mock(ModelMapper.class);
        imageController = new ImageController(imageService, modelMapper);
    }

    @Test
    void uploadImage_Success() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(file.getContentType()).thenReturn("image/png");
        when(file.getBytes()).thenReturn("someImageBytes".getBytes());

        ImageBindingModel img = new ImageBindingModel("image.png", "image/png", compressBytes("someImageBytes".getBytes()));
        ImageServiceModel imgServiceModel = new ImageServiceModel();

        when(modelMapper.map(any(ImageBindingModel.class), eq(ImageServiceModel.class))).thenReturn(imgServiceModel);

        // Act
        ResponseEntity.BodyBuilder responseBuilder = imageController.uploadImage(file);
        ResponseEntity<?> response = responseBuilder.build();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(imageService).upload(imgServiceModel);
    }

    @Test
    void uploadImage_IOException() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(file.getContentType()).thenReturn("image/png");
        when(file.getBytes()).thenThrow(new IOException("File not found"));

        // Act & Assert
        assertThrows(IOException.class, () -> imageController.uploadImage(file));
    }

    @Test
    void getImage_Success() throws IOException {
        // Arrange
        String imageName = "image.png";
        ImageServiceModel imgServiceModel = new ImageServiceModel();
        imgServiceModel.setName(imageName);
        imgServiceModel.setType("image/png");
        imgServiceModel.setPicByte(compressBytes("someImageBytes".getBytes()));

        ImageViewModel imgViewModel = new ImageViewModel(imageName, "image/png", "decompressedImageBytes".getBytes());
        when(imageService.findByName(imageName)).thenReturn(imgServiceModel);
        when(modelMapper.map(any(ImageServiceModel.class), eq(ImageViewModel.class))).thenReturn(imgViewModel);

        // Act
        ImageViewModel response = imageController.getImage(imageName);

        // Assert
        assertEquals(imageName, response.getName());
        assertEquals("image/png", response.getType());
    }

    @Test
    void editImage_Success() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(file.getContentType()).thenReturn("image/png");
        when(file.getBytes()).thenReturn("someImageBytes".getBytes());

        ImageBindingModel img = new ImageBindingModel("image.png", "image/png", compressBytes("someImageBytes".getBytes()));
        ImageServiceModel imgServiceModel = new ImageServiceModel();

        when(modelMapper.map(any(ImageBindingModel.class), eq(ImageServiceModel.class))).thenReturn(imgServiceModel);

        // Act
        ResponseEntity.BodyBuilder responseBuilder = imageController.editImage(file);
        ResponseEntity<?> response = responseBuilder.build();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(imageService).edit(imgServiceModel);
    }

    @Test
    void editImage_IOException() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(file.getContentType()).thenReturn("image/png");
        when(file.getBytes()).thenThrow(new IOException("File not found"));

        // Act & Assert
        assertThrows(IOException.class, () -> imageController.editImage(file));
    }

    @Test
    void deleteImage_Success() throws IOException {
        // Arrange
        String imageName = "image.png";

        // Act
        ResponseEntity.BodyBuilder responseBuilder = imageController.deleteImage(imageName);
        ResponseEntity<?> response = responseBuilder.build();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(imageService).delete(imageName);
    }

    @Test
    void decompressBytes_Success() throws IOException {
        // Arrange
        byte[] input = "someData".getBytes();
        byte[] compressed = ImageController.compressBytes(input);

        // Act
        byte[] decompressed = ImageController.decompressBytes(compressed);

        // Assert
        assertArrayEquals(input, decompressed); // Decompressed data should match the original
    }

    // Utility methods for compressing and decompressing bytes used in tests
    private static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) throws IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}