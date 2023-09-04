package com.fotografocomvc.api.controller;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.AuthResponse;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.api.security.JwtGenerator;
import com.fotografocomvc.core.mapper.PhotographerMapper;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.model.Role;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.service.PhotographerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/photographer")
public class PhotographerController {
    private final PhotographerService photographerService;
    private final PhotographerMapper photographerMapper;

    private final BaseUserRepository baseUserRepository;

    private final JwtGenerator tokenGenerator;

    public PhotographerController(PhotographerService photographerService, PhotographerMapper photographerMapper, BaseUserRepository baseUserRepository, JwtGenerator tokenGenerator) {
        this.photographerService = photographerService;
        this.photographerMapper = photographerMapper;
        this.baseUserRepository = baseUserRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @Operation(tags = {"Photographer"}, description = "Create photographer")
    @PostMapping
    @ResponseBody
    public ResponseEntity<PhotographerResponse> createPhotographer(@RequestBody @Valid PhotographerRequest photographerRequest, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User has authorities: " + userDetails.getAuthorities());

        BaseUser baseUser = baseUserRepository.findByUsername(userDetails.getUsername()).get();
        Photographer photographer = photographerMapper.requestToPhotographer(photographerRequest);
        photographer.setBaseUser(baseUser);
        Photographer createdPhotographer = photographerService.save(photographer);

        PhotographerResponse photographerResponse = photographerMapper.photographerToResponse(createdPhotographer);

        return ResponseEntity.ok(photographerResponse);

    }


    @Operation(tags = {"Photographer"}, description = "Get photographer")
    @GetMapping
    @ResponseBody
    public ResponseEntity<PhotographerResponse> getPhotographer(@RequestBody @Valid PhotographerRequest photographerRequest, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User has authorities: " + userDetails.getAuthorities());

        BaseUser baseUser = baseUserRepository.findByUsername(userDetails.getUsername()).get();
        Photographer photographer = photographerMapper.requestToPhotographer(photographerRequest);
        photographer.setBaseUser(baseUser);
        Photographer createdPhotographer = photographerService.save(photographer);

        PhotographerResponse photographerResponse = photographerMapper.photographerToResponse(createdPhotographer);

        return ResponseEntity.ok(photographerResponse);

    }


    // updateByPhotographer - get the logged in photographer and update their profile;
}
