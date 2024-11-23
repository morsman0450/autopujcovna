package com.autopujcovna.autopujcovna.tests;


import com.autopujcovna.autopujcovna.entity.Customer;
import com.autopujcovna.autopujcovna.repository.CustomerRepository;
import com.autopujcovna.autopujcovna.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    public CustomerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.getCustomerById(1L);

        assertNotNull(foundCustomer);
        assertEquals(1L, foundCustomer.get().getId());
        verify(customerRepository, times(1)).findById(1L);
    }
}

