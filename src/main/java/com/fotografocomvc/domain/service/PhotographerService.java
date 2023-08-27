package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.Photographer;

public interface PhotographerService {

    Photographer save(Photographer photographer);
    Photographer findById(Long id);
    Photographer update(Photographer photographer);
    // Photographer findByBaseUserEmail(String email);

}
