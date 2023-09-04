package com.fotografocomvc.api.http.resources.request;

import com.fotografocomvc.domain.model.enums.RolesAvaliable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCustomerRequest {

    private String username;
    private String password;
    private String name;
}
