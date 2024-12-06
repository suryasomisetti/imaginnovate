package com.test.imaginnovate.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.imaginnovate.dto.EmployeeTaxDTO;
import com.test.imaginnovate.entity.Employee;
import com.test.imaginnovate.exception.NoEmployeesFoundException;
import com.test.imaginnovate.repository.EmployeeRepository;
import com.test.imaginnovate.utils.TaxUtils;

@Service
public class EmployeeTaxService {

    private final EmployeeRepository employeeRepository;

    public EmployeeTaxService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeTaxDTO calculateEmployeeTax(Long employeeId) throws NoEmployeesFoundException {
    	
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoEmployeesFoundException("Employee not found with Id: "+ employeeId));

        return TaxUtils.calculateEmployeeTax(employee);
    }
    
    public List<EmployeeTaxDTO> calculateTaxForCurrentFinancialYear() throws NoEmployeesFoundException {
    	
        List<Employee> employees = employeeRepository.findAll();
        
        if (employees == null || employees.isEmpty()) {
        	throw new NoEmployeesFoundException("No employees found in the database");
        }
        
        return employees.stream()
                .map(TaxUtils::calculateEmployeeTax)
                .collect(Collectors.toList());
    }
    
}
