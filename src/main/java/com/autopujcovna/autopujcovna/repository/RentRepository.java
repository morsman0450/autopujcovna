package com.autopujcovna.autopujcovna.repository;

import com.autopujcovna.autopujcovna.entity.Car;
import com.autopujcovna.autopujcovna.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RentRepository extends JpaRepository<Rent, Long> {
    boolean existsByCarAndReturnDateAfter(Car car, LocalDate date);
}

