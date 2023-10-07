package com.fotografocomvc.api.http.resources.request;

import com.fotografocomvc.domain.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerRequest {

    @Schema(description = "Photographer's name")
    private String name;

    @Schema(description = "Photographer's gender")
    private Gender gender;

    @Schema(description = "Photographer's bio")
    private String bio;

    @Schema(description = "Photographer's phone")
    private String phone;

    @Schema(description = "Photographer's aboutMe")
    private String aboutMe;

    @Schema(description = "Photographer's aboutMe")
    private String shortInfo;

    @Schema(description = "Photographer's location")
    private Long locationId;
}
