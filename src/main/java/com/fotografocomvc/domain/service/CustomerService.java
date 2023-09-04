package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.Customer;

import java.util.Optional;

public interface CustomerService {

    Customer save(Customer customer);
    Customer findById(Long id);
    Customer update(Customer customer);

}
