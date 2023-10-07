package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    List<Location> findAllLocation();

    Optional<Location> findById(Long id);

}
