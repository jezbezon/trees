package com.selflearn.tree.controller;

import com.selflearn.tree.datamapper.ImageDTO;
import com.selflearn.tree.model.Image;
import com.selflearn.tree.resposeClass.ResponseClass;
import com.selflearn.tree.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("create/{productId}")
    public ResponseClass createImage(@RequestParam List<MultipartFile> files, @PathVariable(value = "productId") long productId){
        return new ResponseClass("00", "Insert success!", imageService.insertImage(productId, files));
    }

    @GetMapping("download/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id){
        Image image = imageService.getImageById(id);
        ByteArrayResource resource = new ByteArrayResource(image.getImage());
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.parseMediaType(image.getFileType())))
                // the below header is use just to downloadable so the client can downlaod it
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<Resource> updateImage(@PathVariable Long id, @RequestParam MultipartFile file){
        Image image = imageService.updateImage(id,file);
        ByteArrayResource resource = new ByteArrayResource(image.getImage());
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.parseMediaType(image.getFileType())))
                .body(resource);
    }
}
