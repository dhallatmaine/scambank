package com.vecowski.scambankcustomer.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public CustomerListDto getCustomers() {
        return new CustomerListDto()
                .setCustomers(customerRepository.findAll());
    }

    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        Customer customer = customerService.createCustomer(createCustomerDto);
        return from(customer);
    }

    private CustomerDto from(Customer customer) {
        return new CustomerDto()
                .setId(customer.getId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName());
    }

}
