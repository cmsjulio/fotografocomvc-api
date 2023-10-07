package com.fotografocomvc.api.http.resources.response;

import com.fotografocomvc.domain.model.Image;
import com.fotografocomvc.domain.model.Location;
import com.fotografocomvc.domain.model.Role;
import com.fotografocomvc.domain.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerResponse {

    @Schema(description = "Photographer's id")
    private Long id;

    @Schema(description = "Photographer's name")
    private String name;

    @Schema(description = "Photographer's gender")
    private Gender gender;

    @Schema(description = "Photographer's bio")
    private String bio;

    @Schema(description = "Photographer's phone")
    private String phone;

    @Schema(description = "Photographer's 'about me'")
    private String aboutMe;

    @Schema(description = "Photographer's 'short info'")
    private String shortInfo;

    @Schema(description = "Photographer's roles")
    private List<Role> roles;

    @Schema(description = "Photographer's location")
    private Location location;

    @Schema(description = "Photographer's profile picture")
    private Long profilePicImageId;


}
