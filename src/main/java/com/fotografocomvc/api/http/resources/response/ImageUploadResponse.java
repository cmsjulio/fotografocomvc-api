package com.fotografocomvc.api.http.resources.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResponse {

    @Schema(description = "Image's id")
    private Long id;

    @Schema(description = "Image's name")
    private String name;



}
