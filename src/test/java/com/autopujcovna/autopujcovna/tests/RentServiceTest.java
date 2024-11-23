package com.autopujcovna.autopujcovna.tests;

import com.autopujcovna.autopujcovna.entity.Car;
import com.autopujcovna.autopujcovna.entity.Customer;
import com.autopujcovna.autopujcovna.entity.Rent;
import com.autopujcovna.autopujcovna.exception.CarAlreadyRentedException;
import com.autopujcovna.autopujcovna.repository.CarRepository;
import com.autopujcovna.autopujcovna.repository.CustomerRepository;
import com.autopujcovna.autopujcovna.repository.RentRepository;
import com.autopujcovna.autopujcovna.service.RentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RentRepository rentRepository;

    @InjectMocks
    private RentService rentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRent() {
        Car car = new Car();
        car.setId(1L);

        Customer customer = new Customer();
        customer.setId(1L);

        Rent rent = new Rent();
        rent.setCar(car);
        rent.setCustomer(customer);
        rent.setPrice(BigDecimal.valueOf(1000));
        rent.setReturnDate(LocalDate.now().plusDays(5));

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);

        Rent createdRent = null;
        try {
            createdRent = rentService.createRent(1L, 1L, LocalDate.now().plusDays(5), BigDecimal.valueOf(1000));
        } catch (CarAlreadyRentedException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(createdRent);
        assertEquals(car, createdRent.getCar());
        assertEquals(customer, createdRent.getCustomer());
        assertEquals(BigDecimal.valueOf(1000), createdRent.getPrice());
        assertEquals(LocalDate.now().plusDays(5), createdRent.getReturnDate());

        verify(rentRepository, times(1)).save(any(Rent.class));
    }

    @Test
    void testCreateRent_CarNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rentService.createRent(1L, 1L, LocalDate.now().plusDays(5), BigDecimal.valueOf(1000));
        });

        assertEquals("Auto nebylo nalezeno.", exception.getMessage());
    }

    @Test
    void testCreateRent_CustomerNotFound() {
        Car car = new Car();
        car.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rentService.createRent(1L, 1L, LocalDate.now().plusDays(5), BigDecimal.valueOf(1000));
        });

        assertEquals("Zákazník nebyl nalezen.", exception.getMessage());
    }

    @Test
    void testCreateRent_CarAlreadyRented() {
        Car car = new Car();
        car.setId(1L);
        car.setCurrentRenter(new Customer());

        Customer customer = new Customer();
        customer.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CarAlreadyRentedException exception = assertThrows(CarAlreadyRentedException.class, () -> {
            rentService.createRent(1L, 1L, LocalDate.now().plusDays(5), BigDecimal.valueOf(1000));
        });

        assertEquals("Auto je již půjčeno jinému zákazníkovi.", exception.getMessage());
    }

    @Test
    void testCreateRent_CustomerAlreadyRentedCar() {
        Car car = new Car();
        car.setId(1L); // Auto

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setRentedCar(new Car());

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rentService.createRent(1L, 1L, LocalDate.now().plusDays(5), BigDecimal.valueOf(1000));
        });

        assertEquals("Zákazník již má půjčené auto.", exception.getMessage());
    }
}
