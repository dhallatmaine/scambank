package com.vecowski.scambankcustomer.customer.address;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateAddressDto {

    @NotBlank
    @Size(min = 1, max = 255)
    private String line1;

    @Size(max = 255)
    private String line2;

    @Size(max = 255)
    private String line3;

    @NotBlank
    @Size(min = 1, max = 255)
    private String city;

    @NotBlank
    @Size(min = 1, max = 255)
    private String state;

    @NotBlank
    @Size(min = 1, max = 9)
    private String zipCode;

    @NotBlank
    @Size(min = 1, max = 255)
    private String country;

}
