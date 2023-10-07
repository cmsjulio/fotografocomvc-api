package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.exception.NotFoundException;
import com.fotografocomvc.domain.model.Customer;
import com.fotografocomvc.domain.model.Location;
import com.fotografocomvc.domain.repository.CustomerRepository;
import com.fotografocomvc.domain.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;

    @Override
    public List<Location> findAllLocation() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }
}
