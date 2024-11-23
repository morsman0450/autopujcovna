package com.autopujcovna.autopujcovna.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int carYear;
    private String color;
    private int km_status;

    @OneToOne(mappedBy = "rentedCar", cascade = CascadeType.ALL)
    @JsonBackReference
    private Customer currentRenter;
}
