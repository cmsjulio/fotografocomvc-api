package com.fotografocomvc.api.http.resources.response;

import com.fotografocomvc.domain.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPhotographerResponse {
    private String email;
    private List<String> roles;
    private String name;
    private String gender;
    private String bio;
    private String phone;
    private String aboutMe;

}
