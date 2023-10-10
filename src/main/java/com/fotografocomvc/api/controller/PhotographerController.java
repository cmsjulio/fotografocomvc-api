package com.fotografocomvc.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fotografocomvc.api.http.resources.response.ImageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.core.mapper.PhotographerMapper;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Gallery;
import com.fotografocomvc.domain.model.Image;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.repository.GalleryRepository;
import com.fotografocomvc.domain.repository.ImageRepository;
import com.fotografocomvc.domain.service.BaseUserService;
import com.fotografocomvc.domain.service.LocationService;
import com.fotografocomvc.domain.service.PhotographerService;
import com.fotografocomvc.domain.util.ImageUtility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/photographer")
public class PhotographerController {
    private final BaseUserService baseUserService;

    private final GalleryRepository galleryRepository;
    private final PhotographerService photographerService;

    private final PhotographerMapper photographerMapper;

    private final LocationService locationService;

    private final ImageRepository imageRepository;

    @Operation(tags = { "Photographer" }, description = "Findall photographer")
    @GetMapping("/public/findAll")
    @ResponseBody
    public ResponseEntity<List<PhotographerResponse>> findAllPhotographer() {
        List<Photographer> photographerList = photographerService.findAll();
        List<PhotographerResponse> photographerResponseList = photographerList.stream()
                .map((photographerMapper::photographerToResponse)).toList();
        return ResponseEntity.ok(photographerResponseList);
    }

    @Operation(tags = { "Photographer" }, description = "Findall photographer by location id")
    @GetMapping("/public/findAllByLocationId/{locationId}")
    @ResponseBody
    public ResponseEntity<List<PhotographerResponse>> findAllPhotographerByLocationId(
            @PathVariable("locationId") Long locationId) {
        List<Photographer> photographerList = photographerService.findAllByLocationId(locationId);
        List<PhotographerResponse> photographerResponseList = photographerList.stream()
                .map((photographerMapper::photographerToResponse)).toList();
        return ResponseEntity.ok(photographerResponseList);
    }

    @Operation(tags = { "Photographer" }, description = "Update photographer's own profile")
    @PutMapping(value = "/private/updateOwnProfile")
    @ResponseBody
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PhotographerResponse> updateOwnProfile(Authentication authentication,
            @Valid @RequestBody PhotographerRequest photographerRequest) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser baseUser = baseUserService.findByUsername(userDetails.getUsername()).get();
        Photographer photographer = photographerService.findByBaseUser(baseUser);

        if (photographerRequest.getName() != null) {
            photographer.setName(photographerRequest.getName());
        }
        if (photographerRequest.getGender() != null) {
            photographer.setGender(photographerRequest.getGender());
        }
        if (photographerRequest.getBio() != null) {
            photographer.setBio(photographerRequest.getBio());
        }
        if (photographerRequest.getPhone() != null) {
            photographer.setPhone(photographerRequest.getPhone());
        }
        if (photographerRequest.getAboutMe() != null) {
            photographer.setAboutMe(photographerRequest.getAboutMe());
        }
        if (photographerRequest.getShortInfo() != null) {
            photographer.setShortInfo(photographerRequest.getShortInfo());
        }
        if (photographerRequest.getLocationId() != null) {
            photographer.setLocation(locationService.findById(photographerRequest.getLocationId()).get());
        }
        // profilePic later

        photographerService.update(photographer);
        PhotographerResponse response = photographerMapper.photographerToResponse(photographer);
        return ResponseEntity.ok(response);

    }

    @Operation(tags = { "Photographer" }, description = "Add image to own photographer's gallery")
    @PostMapping(value = "/private/gallery", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PhotographerResponse> addImageToGallery(Authentication authentication,
            @RequestPart(value = "image") MultipartFile file,
            @RequestPart(value = "imageDescription") String imageDescription) throws Exception {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser baseUser = baseUserService.findByUsername(userDetails.getUsername()).get();
        Photographer photographer = photographerService.findByBaseUser(baseUser);

        Gallery gallery = photographer.getGallery();

        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .formatType(file.getContentType())
                .originalImage(ImageUtility.compressImage(file.getBytes()))
                .gallery(gallery)
                .description(imageDescription)
                .build());

        PhotographerResponse response = photographerMapper.photographerToResponse(photographer);
        return ResponseEntity.ok(response);

    }


    @Operation(tags = { "Photographer" }, description = "Find all images from photographer's gallery")
    @GetMapping("/public/gallery/{photographerId}")
    @ResponseBody
    public ResponseEntity<List<ImageResponse>> findAllImagesByGallery(@Valid @PathVariable("photographerId") Long photographerId) {
        Photographer photographer = photographerService.findById(photographerId);
        Long galleryId = photographer.getGallery().getId();

        List<Image> imageList = imageRepository.findAllByGalleryId(galleryId);
        List<ImageResponse> imageResponseList = imageList.stream().map((image -> ImageResponse.builder()
                .id(image.getId())
                .name(image.getName())
                .description(image.getDescription())
                .build())).collect(Collectors.toList());
        return ResponseEntity.ok(imageResponseList);
    }


    @PutMapping(value = "/private/updateProfilePic", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PhotographerResponse> updateProfilePicture(Authentication authentication,
            @RequestParam("image") MultipartFile file)
            throws Exception {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser baseUser = baseUserService.findByUsername(userDetails.getUsername()).get();
        Photographer photographer = photographerService.findByBaseUser(baseUser);

        Image savedImage = imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .formatType(file.getContentType())
                .originalImage(ImageUtility.compressImage(file.getBytes()))
                .build());

        photographer.setProfilePic(savedImage);

        Photographer updatedPhotographer = photographerService.update(photographer);

        return ResponseEntity.status(HttpStatus.OK)
                .body(photographerMapper.photographerToResponse(updatedPhotographer));
    }

    @GetMapping(path = { "/public/getImageDetails/{id}" })
    public ResponseEntity<Image> getImageDetails(@PathVariable("id") Long id) throws IOException {

        final Optional<Image> dbImage = imageRepository.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(Image.builder()
                .id(id)
                .name(dbImage.get().getName())
                .formatType(dbImage.get().getFormatType())
                .description(dbImage.get().getDescription())
                .originalImage(ImageUtility.decompressImage(dbImage.get().getOriginalImage())).build());
    }

    @GetMapping(path = { "/public/getImage/{id}" })
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {

        final Optional<Image> dbImage = imageRepository.findById(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getFormatType()))
                .body(ImageUtility.decompressImage(dbImage.get().getOriginalImage()));
    }

}
