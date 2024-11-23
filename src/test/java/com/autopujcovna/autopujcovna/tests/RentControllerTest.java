package com.autopujcovna.autopujcovna.tests;

import com.autopujcovna.autopujcovna.controller.RentController;
import com.autopujcovna.autopujcovna.entity.Rent;
import com.autopujcovna.autopujcovna.service.CarService;
import com.autopujcovna.autopujcovna.service.RentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class RentControllerTest {

    @Mock
    private RentService rentService;

    @InjectMocks
    private RentController rentController;

    private MockMvc mockMvc;

    private Rent rent;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(rentController).build();

        rent = new Rent();
        rent.setId(1L);
        rent.setRentDate(LocalDate.now());
        rent.setReturnDate(LocalDate.now().plusDays(3));
        rent.setPrice(new BigDecimal("150.00"));
    }

    @Test
    public void testCreateRent() throws Exception {
        Long carId = 1L;
        Long customerId = 1L;
        LocalDate returnDate = LocalDate.now().plusDays(3);
        BigDecimal price = new BigDecimal("150.00");

        when(rentService.createRent(carId, customerId, returnDate, price)).thenReturn(rent);

        mockMvc.perform(post("/api/rents")
                        .param("carId", carId.toString())
                        .param("customerId", customerId.toString())
                        .param("returnDate", returnDate.toString())
                        .param("price", price.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value("150.00"));
    }

    @Test
    public void testGetRentById() throws Exception {
        Long rentId = 1L;
        when(rentService.getRentById(rentId)).thenReturn(rent);

        mockMvc.perform(get("/api/rents/{rentId}", rentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value("150.00"));
    }

    @Test
    public void testUpdateRent() throws Exception {
        Long rentId = 1L;
        Long carId = 1L;
        Long customerId = 1L;
        LocalDate returnDate = LocalDate.now().plusDays(3);
        BigDecimal price = new BigDecimal("200.00");

        when(rentService.updateRent(rentId, carId, customerId, returnDate, price)).thenReturn(rent);

        mockMvc.perform(put("/api/rents/{rentId}", rentId)
                        .param("carId", carId.toString())
                        .param("customerId", customerId.toString())
                        .param("returnDate", returnDate.toString())
                        .param("price", price.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value("150.00"));
    }

    @Test
    public void testDeleteRent() throws Exception {
        Long rentId = 1L;
        doNothing().when(rentService).deleteRent(rentId);

        mockMvc.perform(delete("/api/rents/{rentId}", rentId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetCustomerRentHistory() throws Exception {
        Long customerId = 1L;
        when(rentService.getCustomerRentHistory(customerId)).thenReturn(Collections.singletonList(rent));

        mockMvc.perform(get("/api/rents/history/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }


}