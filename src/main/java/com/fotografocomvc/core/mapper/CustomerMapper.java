package com.fotografocomvc.core.mapper;

import com.fotografocomvc.api.http.resources.request.PhotographerRequest;
import com.fotografocomvc.api.http.resources.response.PhotographerResponse;
import com.fotografocomvc.api.http.resources.response.RegisterCustomerResponse;
import com.fotografocomvc.domain.model.Customer;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.model.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    public RegisterCustomerResponse customerToRegisterResponse(Customer customer) {
        return RegisterCustomerResponse.builder()
                .email(customer.getBaseUser().getUsername())
                .name(customer.getName())
                .roles(customer.getBaseUser().getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }
}
