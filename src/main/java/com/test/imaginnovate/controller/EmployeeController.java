package com.test.imaginnovate.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;

import com.test.imaginnovate.dto.EmployeeDTO;
import com.test.imaginnovate.entity.Employee;
import com.test.imaginnovate.exception.NoEmployeesFoundException;
import com.test.imaginnovate.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Get all Employees", 
            description = "This endpoint retrieves all employees from the system."
        )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees"),
        @ApiResponse(responseCode = "404", description = "No employees found")
    })
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employees);
    }

    @Operation(
        summary = "Get Employee by ID", 
        description = "This endpoint retrieves a specific employee by their ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }

    @Operation(
        summary = "Create a new Employee", 
        description = "This endpoint creates a new employee with the provided details."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Employee created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input, validation failed")
    })
    @PostMapping("/save")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.saveEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @Operation(
        summary = "Create new Employees", 
        description = "This endpoint creates multiple employees with the provided details."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Employees created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input, validation failed")
    })
    @PostMapping("/saveall")
    public ResponseEntity<List<Employee>> createEmployees(@RequestBody @Valid List<EmployeeDTO> employeeDTOs) {
        List<Employee> savedEmployees = employeeService.saveEmployees(employeeDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployees);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessages = new StringBuilder("Validation failed for the following fields: ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.append(fieldError.getField())
                         .append(" (")
                         .append(fieldError.getDefaultMessage())
                         .append("), ");
        }
        String errorMessage = errorMessages.toString().replaceAll(", $", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(NoEmployeesFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(NoEmployeesFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
