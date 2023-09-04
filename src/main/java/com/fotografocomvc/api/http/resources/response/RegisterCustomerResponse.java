package com.fotografocomvc.api.http.resources.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCustomerResponse {
    private String email;
    private List<String> roles;
    private String name;

}
