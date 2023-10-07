package com.fotografocomvc.core.mapper;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.api.http.resources.response.RegisterPhotographerResponse;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.model.Role;
import com.fotografocomvc.domain.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Component
@RequiredArgsConstructor
public class PhotographerMapper {
    private final LocationService locationService;
    public PhotographerResponse photographerToResponse (Photographer photographer){
        PhotographerResponse photographerResponse= new PhotographerResponse();


        photographerResponse = PhotographerResponse.builder()
                .id(photographer.getId())
                .name(photographer.getName())
                .gender(photographer.getGender())
                .bio(photographer.getBio())
                .phone(photographer.getPhone())
                .aboutMe(photographer.getAboutMe())
//                .username(photographer.getBaseUser().getUsername())
                .shortInfo(photographer.getShortInfo())
                .location(photographer.getLocation())
                .build();

        if (photographer.getProfilePic()!=null){ photographerResponse.setProfilePicImageId(photographer.getProfilePic().getId());}

        photographerResponse.setRoles(photographer.getBaseUser().getRoles());

        return photographerResponse;
    }

    public Photographer requestToPhotographer(PhotographerRequest photographerRequest) {
//        Photographer photographer = new Photographer();

//        if (photographerRequest.getName()!=null) {photographer.setName(photographerRequest.getName());}
//        if (photographerRequest.getGender()!=null){photographer.setGender(photographerRequest.getGender());}
//        if (photographerRequest.getBio()!=null){photographer.setBio(photographerRequest.getBio());}
//        if (photographerRequest.getPhone()!=null){photographer.setPhone(photographerRequest.getPhone());}
//        if (photographerRequest.getAboutMe()!=null){photographer.setAboutMe(photographerRequest.getAboutMe());}
//        if (photographerRequest.getLocationId()!=null){photographer.setLocation(locationService.findById(photographerRequest.getLocationId()).get());}

        return null;
    }

    public RegisterPhotographerResponse photographerToRegisterResponse(Photographer photographer) {
        return RegisterPhotographerResponse.builder()
                .email(photographer.getBaseUser().getUsername())
                .roles(photographer.getBaseUser().getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .name(photographer.getName())
                .aboutMe(photographer.getAboutMe())
                .phone(photographer.getPhone())
                .gender(valueOf(photographer.getGender()))
                .bio(photographer.getBio())
                .build();
    }
}
