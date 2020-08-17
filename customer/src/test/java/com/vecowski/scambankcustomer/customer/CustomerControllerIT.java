package com.vecowski.scambankcustomer.customer;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import com.vecowski.scambankcustomer.PostgresMockConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { PostgresMockConfiguration.class })
public class CustomerControllerIT {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomers() {
        Customer customer = new Customer()
                .setId(UUID.randomUUID())
                .setFirstName("Test1")
                .setLastName("Test2");
        customerRepository.save(customer);

        ResponseEntity<CustomerListDto> response =
                testRestTemplate.getForEntity("http://localhost:" + port + "/customers", CustomerListDto.class);

        CustomerListDto customerListDto = response.getBody();
        assertNotNull(customerListDto);
        assertNotNull(customerListDto.getCustomers());
    }

}
