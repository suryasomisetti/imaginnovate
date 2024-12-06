package com.test.imaginnovate.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.test.imaginnovate.config.CustomLocalDateDeserializer;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull
    private String email;

    @ElementCollection
    @CollectionTable(name = "employee_phone_numbers", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "phone_number", nullable = false)
    @NotEmpty
    private List<String> phoneNumbers;

    @Column(name = "doj", nullable = false)
    @NotNull
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate doj;

    @Column(name = "salary", nullable = false)
    @NotNull
    private Double salary;
}
