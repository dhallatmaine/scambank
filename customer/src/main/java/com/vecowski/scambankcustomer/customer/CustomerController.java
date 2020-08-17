package com.vecowski.scambankcustomer.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public CustomerListDto getCustomers() {
        return new CustomerListDto()
                .setCustomers(customerRepository.findAll());
    }

}
