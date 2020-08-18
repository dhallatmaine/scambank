package com.vecowski.scambankcustomer.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.vecowski.scambankcustomer.PostgresMockConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { PostgresMockConfiguration.class })
public class CustomerControllerIT {

    @Test
    public void getAllCustomers() {
        givenCreateCustomerDto();
        givenCreatedCustomer();
        whenGetAllCustomers();
        thenCustomersAreReturned();
    }

    @Test
    public void createCustomer() {
        givenCreateCustomerDto();
        whenCreateCustomer();
        thenCustomerIsCreated();
    }

    @Before
    public void before() {
        customerRepository.deleteAllInBatch();
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    // Inputs
    private CreateCustomerDto createCustomerDto;

    // Outputs
    private ResponseEntity<CustomerDto> createCustomerResponse;
    private ResponseEntity<CustomerListDto> getAllCustomersResponse;

    /*
     * GIVEN
     */
    private void givenCreateCustomerDto() {
        createCustomerDto = RandomCustomerUtil.randomCreateCustomerDto();
    }

    private void givenCreatedCustomer() {
        customerService.createCustomer(createCustomerDto);
    }

    /*
     * WHEN
     */
    private void whenCreateCustomer() {
        createCustomerResponse = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/customer",
                createCustomerDto,
                CustomerDto.class);
    }

    private void whenGetAllCustomers() {
        getAllCustomersResponse = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/customer/all",
                CustomerListDto.class);
    }

    /*
     * THEN
     */
    private void thenCustomerIsCreated() {
        assertEquals(HttpStatus.OK.value(), createCustomerResponse.getStatusCodeValue());
        CustomerDto customerDto = createCustomerResponse.getBody();
        assertNotNull(customerDto);
        assertEquals(createCustomerDto.getFirstName(), customerDto.getFirstName());
        assertEquals(createCustomerDto.getLastName(), customerDto.getLastName());
    }

    private void thenCustomersAreReturned() {
        assertEquals(HttpStatus.OK.value(), getAllCustomersResponse.getStatusCodeValue());
        CustomerListDto customerListDto = getAllCustomersResponse.getBody();
        assertNotNull(customerListDto);
        assertNotNull(customerListDto.getCustomers());
    }

}
