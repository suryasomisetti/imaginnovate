package com.test.imaginnovate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTaxDTO {

    @NotEmpty(message = "Employee code is mandatory")
    private Long employeeId;

    @NotEmpty(message = "First name is mandatory")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    private String lastName;

    @Positive(message = "Yearly salary should be positive")
    private double yearlySalary;

    @Positive(message = "Tax amount should be positive")
    private double taxAmount;

    @Positive(message = "Cess amount should be positive")
    private double cessAmount;
}
