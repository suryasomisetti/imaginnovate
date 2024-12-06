package com.test.imaginnovate.utils;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import com.test.imaginnovate.dto.EmployeeTaxDTO;
import com.test.imaginnovate.entity.Employee;

public class TaxUtils {

    public static EmployeeTaxDTO calculateEmployeeTax(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        LocalDate startOfYear = LocalDate.of(currentYear, Month.APRIL, 1);
        LocalDate endOfYear = LocalDate.of(currentYear + 1, Month.MARCH, 31);
        LocalDate doj = employee.getDoj();

        if (doj.isBefore(startOfYear)) {
            doj = startOfYear;
        }

        long monthsWorked = calculateMonthsWorked(doj, startOfYear, endOfYear);

        double monthlySalary = employee.getSalary();
        double adjustedSalary = 0;

        if (doj.isAfter(startOfYear) && doj.getMonth() != startOfYear.getMonth()) {
            long daysInFirstMonth = doj.lengthOfMonth() - doj.getDayOfMonth() + 1;
            double dailySalary = monthlySalary / doj.lengthOfMonth();
            adjustedSalary += dailySalary * daysInFirstMonth;
            adjustedSalary += monthlySalary * (monthsWorked - 1);
        } else {
            adjustedSalary += monthlySalary * monthsWorked;
        }

        double yearlySalary = adjustedSalary;

        double taxAmount = calculateTaxAmount(yearlySalary);
        double cessAmount = calculateCessAmount(yearlySalary);

        return new EmployeeTaxDTO(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                yearlySalary,
                taxAmount,
                cessAmount
        );
    }

    public static long calculateMonthsWorked(LocalDate doj, LocalDate startOfYear, LocalDate endOfYear) {
        if (doj.isAfter(endOfYear)) {
            return 0;
        }

        long monthsBetween = ChronoUnit.MONTHS.between(doj, endOfYear);
        return monthsBetween >= 0 ? monthsBetween + 1 : 0;
    }

    public static double calculateTaxAmount(double yearlySalary) {
        if (yearlySalary <= 250000) {
            return 0;
        } else if (yearlySalary <= 500000) {
            return (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            return (yearlySalary - 500000) * 0.10 + 12500;
        } else {
            return (yearlySalary - 1000000) * 0.20 + 37500;
        }
    }

    public static double calculateCessAmount(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }
}
