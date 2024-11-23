package com.autopujcovna.autopujcovna.tests;

import com.autopujcovna.autopujcovna.controller.CustomerController;
import com.autopujcovna.autopujcovna.entity.Customer;
import com.autopujcovna.autopujcovna.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    public CustomerControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerService.createCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(customerService, times(1)).createCustomer(customer);
    }
}
