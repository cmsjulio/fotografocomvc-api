package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    List<Image> findAllByGalleryId(Long galleryId);
}
