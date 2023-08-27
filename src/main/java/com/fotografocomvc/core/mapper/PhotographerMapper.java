package com.fotografocomvc.core.mapper;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.domain.model.Photographer;
import org.springframework.stereotype.Component;

@Component
public class PhotographerMapper {
    public PhotographerResponse photographerToResponse (Photographer photographer){
        return PhotographerResponse.builder()
                .id(photographer.getId())
                .name(photographer.getName())
                .gender(photographer.getGender())
                .bio(photographer.getBio())
                .phone(photographer.getPhone())
                .aboutMe(photographer.getAboutMe())
                .build();
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
}
