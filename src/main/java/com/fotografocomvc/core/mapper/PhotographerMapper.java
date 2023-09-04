package com.fotografocomvc.core.mapper;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.api.http.resources.response.RegisterPhotographerResponse;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.model.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PhotographerMapper {
    public PhotographerResponse photographerToResponse (Photographer photographer){
        PhotographerResponse photographerResponse = PhotographerResponse.builder()
                .id(photographer.getId())
                .name(photographer.getName())
                .gender(photographer.getGender())
                .bio(photographer.getBio())
                .phone(photographer.getPhone())
                .aboutMe(photographer.getAboutMe())
                .build();

        photographerResponse.setRoles(photographer.getBaseUser().getRoles());

        return photographerResponse;
    }

    public Photographer requestToPhotographer(PhotographerRequest photographerRequest) {
        return  Photographer.builder()
                .name(photographerRequest.getName())
                .gender(photographerRequest.getGender())
                .bio(photographerRequest.getBio())
                .phone(photographerRequest.getPhone())
                .aboutMe(photographerRequest.getAboutMe())
                .build();
    }

    public RegisterPhotographerResponse photographerToRegisterResponse(Photographer photographer) {
        return RegisterPhotographerResponse.builder()
                .email(photographer.getBaseUser().getUsername())
                .roles(photographer.getBaseUser().getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .name(photographer.getName())
                .aboutMe(photographer.getAboutMe())
                .phone(photographer.getPhone())
                .gender(photographer.getGender().toString())
                .bio(photographer.getBio())
                .build();
    }
}
