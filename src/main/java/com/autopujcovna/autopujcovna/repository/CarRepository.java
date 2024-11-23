package com.autopujcovna.autopujcovna.repository;

import com.autopujcovna.autopujcovna.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
