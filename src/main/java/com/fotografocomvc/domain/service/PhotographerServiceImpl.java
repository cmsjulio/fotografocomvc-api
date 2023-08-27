package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.exception.NotFoundException;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.repository.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotographerServiceImpl implements PhotographerService{

    private final PhotographerRepository photographerRepository;

    @Override
    public Photographer save(Photographer photographer) {
        return photographerRepository.save(photographer);
    }

    @Override
    public Photographer findById(Long id) {
        return photographerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Photographer not found"));
    }

    @Override
    public Photographer update(Photographer photographer) {
        photographerRepository.findById(photographer.getId())
                .orElseThrow(() -> new NotFoundException("Photographer not found"));
        photographerRepository.save(photographer);
        return photographer;
    }
}
