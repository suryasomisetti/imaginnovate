package com.test.imaginnovate.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.test.imaginnovate.config.CustomLocalDateDeserializer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotNull(message = "First name is required")
    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, message = "First name should have at least 2 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, message = "Last name should have at least 2 characters")
    private String lastName;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Date of Joining is required")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate doj;

    @NotNull(message = "Salary is required")
    private Double salary;

    @NotNull(message = "Phone numbers are required")
    @NotEmpty(message = "Phone numbers cannot be empty")
    private List<String> phoneNumbers;
}
