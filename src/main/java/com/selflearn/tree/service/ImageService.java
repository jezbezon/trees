package com.selflearn.tree.service;

import com.selflearn.tree.datamapper.ImageDTO;
import com.selflearn.tree.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageDTO> insertImage(long productId, List<MultipartFile> file);

//    ImageDTO getImageById(long id);

    Image getImageById(long id);

    List<ImageDTO> getAllImage();

    Image updateImage(long productId, MultipartFile file);

//    ImageDTO updateImage(long productId, ImageDTO imageDTO);

    void deleteImage(long id);

}
