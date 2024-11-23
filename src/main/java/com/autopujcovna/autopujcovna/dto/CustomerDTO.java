package com.autopujcovna.autopujcovna.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CustomerDTO {

    @NotBlank(message = "Jméno je povinné.")
    private String Name;


    @NotBlank(message = "Email je povinný.")
    @Email(message = "Email musí být ve správném formátu.")
    private String email;

    @NotBlank(message = "Telefon je povinný.")
    private String phone;

}
