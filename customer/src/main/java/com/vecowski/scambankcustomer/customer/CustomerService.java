package com.vecowski.scambankcustomer.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = from(createCustomerDto);
        return customerRepository.save(customer);
    }

    private Customer from(CreateCustomerDto createCustomerDto) {
        return new Customer()
                .setId(UUID.randomUUID())
                .setFirstName(createCustomerDto.getFirstName())
                .setLastName(createCustomerDto.getLastName());
    }

}
