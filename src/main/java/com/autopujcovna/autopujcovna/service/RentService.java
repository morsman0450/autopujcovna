package com.autopujcovna.autopujcovna.service;

import com.autopujcovna.autopujcovna.entity.Car;
import com.autopujcovna.autopujcovna.entity.Customer;
import com.autopujcovna.autopujcovna.entity.Rent;
import com.autopujcovna.autopujcovna.exception.CarAlreadyRentedException;
import com.autopujcovna.autopujcovna.exception.CustomerAlreadyHasRentException;
import com.autopujcovna.autopujcovna.exception.RentNotFoundException;
import com.autopujcovna.autopujcovna.exception.ResourceNotFoundException;
import com.autopujcovna.autopujcovna.repository.CarRepository;
import com.autopujcovna.autopujcovna.repository.CustomerRepository;
import com.autopujcovna.autopujcovna.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Rent createRent(Long carId, Long customerId, LocalDate returnDate, BigDecimal price) throws CarAlreadyRentedException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Auto nebylo nalezeno."));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Zákazník nebyl nalezen."));

        if (car.getCurrentRenter() != null) {
            throw new CarAlreadyRentedException("Auto je již půjčeno jinému zákazníkovi.");
        }

        if (customer.getRentedCar() != null) {
            throw new CustomerAlreadyHasRentException("Zákazník již má půjčené auto.");
        }

        Rent rent = new Rent();
        rent.setCar(car);
        rent.setCustomer(customer);
        rent.setReturnDate(returnDate);
        rent.setPrice(price);

        car.setCurrentRenter(customer);
        customer.setRentedCar(car);
        return rentRepository.save(rent);
    }

    public List<Rent> getCustomerRentHistory(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Zákazník nenalezen."));
        return customer.getRentHistory();
    }
    public Rent getRentById(Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> new RentNotFoundException("Pronájem s ID " + rentId + " nebyl nalezen."));
    }
    public void deleteRent(Long rentId) {
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new RentNotFoundException("Pronájem s ID " + rentId + " nebyl nalezen."));
        rentRepository.delete(rent);
    }
    public Rent updateRent(Long rentId, Long carId, Long customerId, LocalDate returnDate, BigDecimal price) throws CarAlreadyRentedException {
        Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new IllegalArgumentException("Půjčka nenalezena"));

        Car car = carRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Auto nenalezeno"));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Zákazník nenalezen"));

        rent.setCar(car);
        rent.setCustomer(customer);
        rent.setReturnDate(returnDate);
        rent.setPrice(price);

        if (rentRepository.existsByCarAndReturnDateAfter(car, LocalDate.now())) {
            throw new CarAlreadyRentedException("Auto je již půjčené.");
        }

        return rentRepository.save(rent);
    }
}

