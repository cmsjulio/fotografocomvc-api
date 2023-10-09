package com.fotografocomvc.api.http.resources.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDescriptionRequest {

    @Schema(description = "Image description")
    private String imageDescription;
}
