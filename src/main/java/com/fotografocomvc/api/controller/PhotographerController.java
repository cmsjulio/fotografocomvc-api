package com.fotografocomvc.api.controller;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.api.security.JwtManager;
import com.fotografocomvc.core.mapper.PhotographerMapper;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.service.BaseUserService;
import com.fotografocomvc.domain.service.PhotographerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/photographer")
public class PhotographerController {
    private final BaseUserService baseUserService;

    public PhotographerController(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @Operation(tags = {"Photographer"}, description = "Create photographer")
    @GetMapping
    @ResponseBody
    public ResponseEntity<String> greetUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User has authorities: " + userDetails.getAuthorities());

        BaseUser baseUser = baseUserService.findByUsername(userDetails.getUsername()).get();


        return ResponseEntity.ok("Bem vindo, " + baseUser.getUsername());

    }

    // updateByPhotographer - get the logged in photographer and update their profile;
}
