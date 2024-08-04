package com.foodorderapp.services.impl;

import com.foodorderapp.models.entity.Image;
import com.foodorderapp.models.service.ImageServiceModel;
import com.foodorderapp.repositories.ImageRepository;
import com.foodorderapp.services.interfaces.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public ImageServiceModel upload(ImageServiceModel imageServiceModel) {
        var img = this.modelMapper.map(imageServiceModel, Image.class);
        var exist = this.imageRepository.findByName(img.getName());
        if(exist != null){
            this.edit(imageServiceModel);
            return imageServiceModel;
        }

        this.imageRepository.save(img);
        return this.modelMapper.map(img, ImageServiceModel.class);
    }

    @Override
    public ImageServiceModel edit(ImageServiceModel imageServiceModel) {
        var imgDB = this.modelMapper.map(this.imageRepository
                .findByName(imageServiceModel.getName()), Image.class);

        imgDB.setName(imageServiceModel.getName());
        imgDB.setType(imageServiceModel.getType());
        imgDB.setPicByte(imageServiceModel.getPicByte());

        return this.modelMapper
                .map(this.imageRepository.saveAndFlush(imgDB),
                    ImageServiceModel.class);
    }

    @Override
    public void delete(String name) {
        this.imageRepository
            .delete(this.modelMapper
                    .map(this.imageRepository.findByName(name),
                            Image.class));
    }

    @Override
    public ImageServiceModel findByName(String name) {
        var img = this.imageRepository.findByName(name);

        return this.modelMapper.map(img, ImageServiceModel.class);
    }
}
