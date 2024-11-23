package com.autopujcovna.autopujcovna.controller;

import com.autopujcovna.autopujcovna.entity.Rent;
import com.autopujcovna.autopujcovna.exception.CarAlreadyRentedException;
import com.autopujcovna.autopujcovna.service.RentService;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    @Autowired
    private RentService rentService;

    @PostMapping
    public ResponseEntity<Rent> createRent(@RequestParam @NotNull Long carId,
                                           @RequestParam @NotNull Long customerId,
                                           @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
                                           @RequestParam @NotNull @DecimalMin(value = "0.0", message = "Cena musí být kladná.") BigDecimal price) throws CarAlreadyRentedException {
        if (returnDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Datum vrácení nemůže být v minulosti.");
        }

        Rent rent = rentService.createRent(carId, customerId, returnDate, price);
        return new ResponseEntity<>(rent, HttpStatus.CREATED);
    }

    @GetMapping("/history/{customerId}")
    public List<Rent> getCustomerRentHistory(@PathVariable Long customerId) {
        return rentService.getCustomerRentHistory(customerId);
    }
    @GetMapping("/{rentId}")
    public Rent getRentById(@PathVariable Long rentId) {
        return rentService.getRentById(rentId);
    }
    @DeleteMapping("/{rentId}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long rentId) {
        rentService.deleteRent(rentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{rentId}")
    public Rent updateRent(@PathVariable Long rentId,
                           @RequestParam @NotNull Long carId,
                           @RequestParam @NotNull Long customerId,
                           @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
                           @RequestParam @NotNull @DecimalMin(value = "0.0", message = "Cena musí být kladná.") BigDecimal price) throws CarAlreadyRentedException {
        if (returnDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Datum vrácení nemůže být v minulosti.");
        }

        return rentService.updateRent(rentId, carId, customerId, returnDate, price);
    }
}
