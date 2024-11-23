package com.autopujcovna.autopujcovna.tests;

import com.autopujcovna.autopujcovna.entity.Car;
import com.autopujcovna.autopujcovna.repository.CarRepository;
import com.autopujcovna.autopujcovna.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    public CarServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCar() {
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");

        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carService.createCar(car);

        assertNotNull(savedCar);
        assertEquals("Toyota", savedCar.getBrand());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testGetCarById() {
        Car car = new Car();
        car.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Optional<Car> foundCar = carService.getCarById(1L);

        assertNotNull(foundCar);
        assertEquals(1L, foundCar.get().getId());
        verify(carRepository, times(1)).findById(1L);
    }
}
