package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.exception.NotFoundException;
import com.fotografocomvc.domain.model.Customer;
import com.fotografocomvc.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;


    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    public Customer update(Customer customer) {
        customerRepository.findById(customer.getId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        customerRepository.save(customer);
        return customer;
    }
}
