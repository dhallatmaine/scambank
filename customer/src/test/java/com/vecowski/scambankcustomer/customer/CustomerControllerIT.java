package com.vecowski.scambankcustomer.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.vecowski.scambankcustomer.PostgresMockConfiguration;
import com.vecowski.scambankcustomer.customer.address.AddressDto;
import com.vecowski.scambankcustomer.customer.address.AddressRepository;
import com.vecowski.scambankcustomer.customer.address.CreateAddressDto;
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

    @Test
    public void createAddress() {
        givenCreateCustomerDto();
        givenCreatedCustomer();
        givenCreateAddressDto();
        whenCreateAddress();
        thenAddressIsCreated();
    }

    @Before
    public void before() {
        addressRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

    // Inputs
    private CreateCustomerDto createCustomerDto;
    private CreateAddressDto createAddressDto;

    // Outputs
    private Customer createdCustomer;
    private ResponseEntity<CustomerDto> createCustomerResponse;
    private ResponseEntity<CustomerListDto> getAllCustomersResponse;
    private ResponseEntity<AddressDto> createAddressResponse;

    /*
     * GIVEN
     */
    private void givenCreateCustomerDto() {
        createCustomerDto = RandomCustomerUtil.randomCreateCustomerDto();
    }

    private void givenCreatedCustomer() {
        createdCustomer = customerService.createCustomer(createCustomerDto);
    }

    private void givenCreateAddressDto() {
        createAddressDto = RandomCustomerUtil.randomCreateAddressDto();
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

    private void whenCreateAddress() {
        createAddressResponse = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/customer/" + createdCustomer.getId() + "/address",
                createAddressDto,
                AddressDto.class);
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

    private void thenAddressIsCreated() {
        assertEquals(HttpStatus.OK.value(), createAddressResponse.getStatusCodeValue());
        AddressDto addressDto = createAddressResponse.getBody();
        assertNotNull(addressDto);
        assertEquals(createAddressDto.getLine1(), addressDto.getLine1());
        assertEquals(createAddressDto.getLine2(), addressDto.getLine2());
        assertEquals(createAddressDto.getLine3(), addressDto.getLine3());
        assertEquals(createAddressDto.getCity(), addressDto.getCity());
        assertEquals(createAddressDto.getState(), addressDto.getState());
        assertEquals(createAddressDto.getCountry(), addressDto.getCountry());
        assertEquals(createAddressDto.getZipCode(), addressDto.getZipCode());
        assertEquals(createdCustomer.getId(), addressDto.getCustomerId());
    }

}
