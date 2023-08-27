package com.fotografocomvc.api.controller;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.core.mapper.PhotographerMapper;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.service.PhotographerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/photographer")
public class PhotographerController {
    private final PhotographerService photographerService;
    private final PhotographerMapper photographerMapper;

    public PhotographerController(PhotographerService photographerService, PhotographerMapper photographerMapper) {
        this.photographerService = photographerService;
        this.photographerMapper = photographerMapper;
    }

    @Operation(tags = {"Photographer"}, description = "Create photographer")
    @PostMapping
    @ResponseBody
    public ResponseEntity<PhotographerResponse> createPhotographer(@RequestBody @Valid PhotographerRequest photographerRequest) {
        Photographer createdPhotographer = photographerService.save(photographerMapper.requestToPhotographer(photographerRequest));
        PhotographerResponse photographerResponse = photographerMapper.photographerToResponse(createdPhotographer);

        return ResponseEntity.ok(photographerResponse);

    }

    // updateByPhotographer - get the logged in photographer and update their profile;
}
