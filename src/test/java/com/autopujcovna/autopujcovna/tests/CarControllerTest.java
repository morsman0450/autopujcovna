package com.autopujcovna.autopujcovna.tests;

import com.autopujcovna.autopujcovna.controller.CarController;
import com.autopujcovna.autopujcovna.entity.Car;
import com.autopujcovna.autopujcovna.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    public CarControllerTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testGetAllCars() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setCarYear(2020);

        when(carService.getAllCars()).thenReturn(Collections.singletonList(car));

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Corolla"));
    }

    @Test
    void testCreateCar() throws Exception {
        Car car = new Car();
        car.setBrand("Honda");
        car.setModel("Civic");
        car.setCarYear(2022);

        when(carService.createCar(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brand\": \"Honda\", \"model\": \"Civic\", \"year\": 2022}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.model").value("Civic"));
    }
}
