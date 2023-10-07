package com.fotografocomvc.api.controller;


import com.fotografocomvc.api.http.resources.response.ImageUploadResponse;
import com.fotografocomvc.domain.model.Image;
import com.fotografocomvc.domain.repository.ImageRepository;
import com.fotografocomvc.domain.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

//    @PostMapping()
//    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("image") MultipartFile file)
//            throws Exception {
//
//        Image watermarkImage = imageRepository.findByName("watermark.png").get();
//        byte[] watermarkBytes = ImageUtility.decompressImage(watermarkImage.getOriginalImage());
//        byte[] resultWatermarkedImageInBytes = ImageUtility.addImageWatermark(watermarkBytes, file.getBytes());
//
//        imageRepository.save(Image.builder()
//                .name(file.getOriginalFilename())
//                .formatType(file.getContentType())
////                .originalImage(ImageUtility.compressImage(file.getBytes()))
//                .originalImage(ImageUtility.compressImage(resultWatermarkedImageInBytes))
//                .build());
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ImageUploadResponse("Image uploaded successfully: " +
//                        file.getOriginalFilename()));
//    }

    @GetMapping(path = {"/details/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        return Image.builder()
                .name(dbImage.get().getName())
                .formatType(dbImage.get().getFormatType())
                .originalImage(ImageUtility.decompressImage(dbImage.get().getOriginalImage())).build();
    }

    @GetMapping(path = {"/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getFormatType()))
                .body(ImageUtility.decompressImage(dbImage.get().getOriginalImage()));
    }
}