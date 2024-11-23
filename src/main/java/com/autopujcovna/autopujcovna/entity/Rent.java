package com.autopujcovna.autopujcovna.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Setter
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    @ManyToOne
    @JsonBackReference
    private Customer customer;

    @NotNull(message = "Datum půjčení je povinné.")
    private LocalDate rentDate;

    @NotNull(message = "Datum vrácení je povinné.")
    private LocalDate returnDate;

    @Column(precision = 10, scale = 2)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal price;

    @PrePersist
    public void prePersist() {
        if (rentDate == null) {
            rentDate = LocalDate.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (returnDate.isBefore(rentDate)) {
            throw new IllegalArgumentException("Datum vrácení nemůže být před datem půjčení.");
        }
    }
}
