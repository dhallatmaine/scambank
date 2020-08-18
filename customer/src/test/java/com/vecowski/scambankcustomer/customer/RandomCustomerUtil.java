package com.vecowski.scambankcustomer.customer;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomCustomerUtil {

    public static CreateCustomerDto randomCreateCustomerDto() {
        return new CreateCustomerDto()
                .setFirstName(RandomStringUtils.randomAlphabetic(255))
                .setLastName(RandomStringUtils.randomAlphabetic(255));
    }

}
