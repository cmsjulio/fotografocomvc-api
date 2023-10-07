package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Photographer;

import java.util.List;

public interface PhotographerService {

    Photographer save(Photographer photographer);
    Photographer findById(Long id);
    Photographer update(Photographer photographer);
    // Photographer findByBaseUserEmail(String email);
    List<Photographer> findAll();

    List<Photographer> findAllByLocationId(Long locationId);

    Photographer findByBaseUser(BaseUser baseUser);

}
