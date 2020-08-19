package com.vecowski.scambankcustomer.customer;

import com.vecowski.scambankcustomer.customer.address.Address;
import com.vecowski.scambankcustomer.customer.address.AddressDto;
import com.vecowski.scambankcustomer.customer.address.AddressService;
import com.vecowski.scambankcustomer.customer.address.CreateAddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/all")
    public CustomerListDto getCustomers() {
        return new CustomerListDto()
                .setCustomers(customerService.findAllCustomers());
    }

    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody final CreateCustomerDto createCustomerDto) {
        Customer customer = customerService.createCustomer(createCustomerDto);
        return from(customer);
    }

    @PostMapping("/{id}/address")
    public AddressDto createAddress(
            @PathVariable("id") final UUID customerId,
            @Valid @RequestBody final CreateAddressDto createAddressDto) {
        Address address = addressService.createAddress(customerId, createAddressDto);
        return from(address);
    }

    private CustomerDto from(final Customer customer) {
        return new CustomerDto()
                .setId(customer.getId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName());
    }

    private AddressDto from(final Address address) {
        return new AddressDto()
                .setId(address.getId())
                .setCustomerId(address.getCustomerId())
                .setLine1(address.getLine1())
                .setLine2(address.getLine2())
                .setLine3(address.getLine3())
                .setCity(address.getCity())
                .setState(address.getState())
                .setZipCode(address.getZipCode())
                .setCountry(address.getCountry());
    }

}
