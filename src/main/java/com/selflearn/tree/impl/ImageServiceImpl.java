package com.selflearn.tree.impl;

import com.selflearn.tree.datamapper.ImageDTO;
import com.selflearn.tree.exceptions.CustomizeException;
import com.selflearn.tree.model.Image;
import com.selflearn.tree.repository.ImageRepository;
import com.selflearn.tree.repository.ProductRepository;
import com.selflearn.tree.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository  imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ImageDTO> insertImage(long productId, List<MultipartFile> files) {
        return productRepository.findById(productId).map(product ->{
            List<ImageDTO> listImageDto = new ArrayList<>();
                try {
                    for(MultipartFile file : files){
                        Image image = new Image();
                        image.setFileName(file.getOriginalFilename());
                        image.setFileType(file.getContentType());
                        image.setImage(file.getBytes());
                        image.setProduct(product);
                        String url = "api/image/download/";
                        image.setViewUrl(url);
                        Image afterSaved = imageRepository.save(image);
                        image.setViewUrl(url + afterSaved.getId());
                        imageRepository.save(afterSaved);

                        ImageDTO imageDTO = new ImageDTO();
                        imageDTO.setId(afterSaved.getId());
                        imageDTO.setFileName(afterSaved.getFileName());
                        imageDTO.setFileType(afterSaved.getFileType());
                        imageDTO.setDownloadUrl(afterSaved.getViewUrl());
                        listImageDto.add(imageDTO);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            return listImageDto;
        }).orElseThrow(()-> new CustomizeException.NotFoundException("This product not found", "02"));

    }


    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id).orElseThrow(()-> new CustomizeException.NotFoundException("Image not found!","02"));
    }

    @Override
    public List<ImageDTO> getAllImage() {
        List<ImageDTO> listImageDto = new ArrayList<>();
        imageRepository.findAll().forEach(image -> {
             ImageDTO imageDTO = new ImageDTO();
             imageDTO.setId(image.getId());
             imageDTO.setFileName(image.getFileName());
             imageDTO.setFileType(imageDTO.getFileType());
             imageDTO.setDownloadUrl(image.getViewUrl());
             listImageDto.add(imageDTO);
         });
           return listImageDto;
    }

    @Override
    public Image updateImage(long id, MultipartFile file) {
        return imageRepository.findById(id).map(image -> {
            try{
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                imageRepository.save(image);
                return getImageById(id);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }).orElseThrow(()-> new CustomizeException.NotFoundException("Image not found!","02"));
    }

    @Override
    public void deleteImage(long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,() -> {
            throw new CustomizeException.NotFoundException("This image not found!");
        });
    }
}
