package com.autopujcovna.autopujcovna.controller;

import com.autopujcovna.autopujcovna.dto.CarDTO;
import com.autopujcovna.autopujcovna.entity.Car;
import com.autopujcovna.autopujcovna.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody @Valid CarDTO carDTO) {
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setCarYear(carDTO.getYear());
        car.setKm_status(carDTO.getKmStatus());

        Car newCar = carService.createCar(car);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody @Valid CarDTO carDTO) {
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setCarYear(carDTO.getYear());
        car.setKm_status(carDTO.getKmStatus());

        Car updatedCar = carService.updateCar(id, car);
        return updatedCar != null ? ResponseEntity.ok(updatedCar) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        return carService.deleteCar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}