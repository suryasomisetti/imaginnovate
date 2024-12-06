package com.test.imaginnovate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.test.imaginnovate.dto.EmployeeTaxDTO;
import com.test.imaginnovate.exception.NoEmployeesFoundException;
import com.test.imaginnovate.service.EmployeeTaxService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class EmployeeTaxController {

    private final EmployeeTaxService employeeTaxService;

    @Autowired
    public EmployeeTaxController(EmployeeTaxService employeeTaxService) {
        this.employeeTaxService = employeeTaxService;
    }

    @Operation(
        summary = "Get Tax Deductions for All Employees",
        description = "Fetches the tax deduction details for employees for the current financial year (April to March)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the tax deductions"),
        @ApiResponse(responseCode = "404", description = "No employees found or data could not be processed")
    })
    @GetMapping("/employees/tax-deductions")
    public ResponseEntity<List<EmployeeTaxDTO>> getTaxDeductions() throws NoEmployeesFoundException {
        List<EmployeeTaxDTO> taxDetails;
        try {
            taxDetails = employeeTaxService.calculateTaxForCurrentFinancialYear();
            return ResponseEntity.ok(taxDetails);
        } catch (NoEmployeesFoundException ex) {
            throw new NoEmployeesFoundException("No tax data found for employees.");
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while fetching tax data.");
        }
    }

    @Operation(
        summary = "Get tax deductions for a specific employee",
        description = "Fetches the tax deductions for a specific employee based on their employee ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tax deductions for employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/employees/{employeeId}/tax-deduction")
    public ResponseEntity<EmployeeTaxDTO> getEmployeeTaxDeduction(@PathVariable Long employeeId) throws NoEmployeesFoundException {
        EmployeeTaxDTO employeeTaxDTO;
        try {
            employeeTaxDTO = employeeTaxService.calculateEmployeeTax(employeeId);
            if (employeeTaxDTO == null) {
                throw new NoEmployeesFoundException("Employee not found for the given ID.");
            }
            return ResponseEntity.ok(employeeTaxDTO);
        } catch (NoEmployeesFoundException ex) {
            throw new NoEmployeesFoundException("No tax data found for employee ID: " + employeeId);
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while fetching tax data for employee.");
        }
    }

    @ExceptionHandler(NoEmployeesFoundException.class)
    public ResponseEntity<String> handleNoEmployeesFoundException(NoEmployeesFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
