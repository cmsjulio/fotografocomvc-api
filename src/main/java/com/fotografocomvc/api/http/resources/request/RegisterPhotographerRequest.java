package com.fotografocomvc.api.http.resources.request;

import com.fotografocomvc.domain.model.enums.Gender;
import com.fotografocomvc.domain.model.enums.RolesAvaliable;
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
public class RegisterPhotographerRequest {

    @Schema(description = "Photographer's username (email)")
    @NotNull(message = "Photographer's username(email) cannot be null")
    private String username;

    @Schema(description = "Photographer's password")
    @NotNull(message = "Photographer's password cannot be null")
    private String password;

    @Schema(description = "Photographer's name")
    @NotNull(message = "Photographer's name cannot be null")
    private String name;

    @Schema(description = "Photographer's gender")
    private Gender gender;

    @Schema(description = "Photographer's bio")
    private String bio;

    @Schema(description = "Photographer's phone")
    private String phone;

    @Schema(description = "Photographer's aboutMe")
    private String aboutMe;
}
