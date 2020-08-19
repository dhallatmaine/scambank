package com.vecowski.scambankcustomer.customer.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(final UUID customerId, final CreateAddressDto createAddressDto) {
        Address address = from(createAddressDto)
                .setCustomerId(customerId);
        return addressRepository.save(address);
    }

    private Address from(final CreateAddressDto createAddressDto) {
        return new Address()
                .setId(UUID.randomUUID())
                .setLine1(createAddressDto.getLine1())
                .setLine2(createAddressDto.getLine2())
                .setLine3(createAddressDto.getLine3())
                .setCity(createAddressDto.getCity())
                .setState(createAddressDto.getState())
                .setCountry(createAddressDto.getCountry())
                .setZipCode(createAddressDto.getZipCode());
    }

}
