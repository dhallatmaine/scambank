package com.vecowski.scambankcustomer.customer;

import com.vecowski.scambankcustomer.customer.address.CreateAddressDto;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomCustomerUtil {

    public static CreateCustomerDto randomCreateCustomerDto() {
        return new CreateCustomerDto()
                .setFirstName(RandomStringUtils.randomAlphabetic(255))
                .setLastName(RandomStringUtils.randomAlphabetic(255));
    }

    public static CreateAddressDto randomCreateAddressDto() {
        return new CreateAddressDto()
                .setLine1(RandomStringUtils.randomAlphabetic(255))
                .setLine2(RandomStringUtils.randomAlphabetic(255))
                .setLine3(RandomStringUtils.randomAlphabetic(255))
                .setCity(RandomStringUtils.randomAlphabetic(255))
                .setState(RandomStringUtils.randomAlphabetic(255))
                .setZipCode(RandomStringUtils.randomAlphabetic(9))
                .setCountry(RandomStringUtils.randomAlphabetic(255));
    }

}
