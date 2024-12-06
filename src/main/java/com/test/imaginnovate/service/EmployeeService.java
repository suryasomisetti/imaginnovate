package com.test.imaginnovate.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.imaginnovate.dto.EmployeeDTO;
import com.test.imaginnovate.entity.Employee;
import com.test.imaginnovate.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.orElse(null); 
    }

    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDoj(employeeDTO.getDoj());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPhoneNumbers(employeeDTO.getPhoneNumbers());

        return employeeRepository.save(employee);
    }
    
    public List<Employee> saveEmployees(List<EmployeeDTO> employeeDTOs) {
        List<Employee> employees = employeeDTOs.stream().map(employeeDTO -> {
            Employee employee = new Employee();
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setDoj(employeeDTO.getDoj());
            employee.setSalary(employeeDTO.getSalary());
            employee.setPhoneNumbers(employeeDTO.getPhoneNumbers());
            return employee;
        }).collect(Collectors.toList());

        return employeeRepository.saveAll(employees);
    }
}
