package org.example.currencyexchange.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserAccountRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @Positive(message = "Intital amount needs to be greater than zero")
    private double initialAmount;
}
