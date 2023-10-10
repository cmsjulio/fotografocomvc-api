package com.fotografocomvc.api.http.resources.response;

import com.fotografocomvc.domain.model.Gallery;
import com.fotografocomvc.domain.model.Location;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.model.Role;
import com.fotografocomvc.domain.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    @Schema(description = "Image's id")
    private Long id;

    @Schema(description = "Image's name")
    private String name;

    @Schema(description = "Image's description")
    private String description;

}
