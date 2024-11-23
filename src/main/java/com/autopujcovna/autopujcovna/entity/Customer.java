package com.autopujcovna.autopujcovna.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Jméno je povinné.")
    private String name;

    @Email(message = "Zadejte platnou e-mailovou adresu.")
    private String email;

    @NotBlank(message = "Telefonní číslo je povinné.")
    @Size(max = 15, message = "Telefonní číslo může mít maximálně 15 znaků.")
    @Pattern(regexp = "\\+?[0-9]+", message = "Telefonní číslo musí obsahovat pouze číslice a případně znak '+'.")
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Rent> rentHistory;

    @OneToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car rentedCar;
    public Customer() {

    }
}
