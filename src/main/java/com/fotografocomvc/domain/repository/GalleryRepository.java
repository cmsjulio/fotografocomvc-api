
package com.fotografocomvc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fotografocomvc.domain.model.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
