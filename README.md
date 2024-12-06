
1. API endpoint to store employee details(Validate the data and throw the errors on invalid data).    
        Create Employee: http://localhost:8080/employees/save  
        Create Employees: http://localhost:8080/employees/saveall  
        
2. API endpoint to return employees' tax deduction for the current financial year(April to March). API should return employee code, first name, last name, yearly salary, tax amount, cess amount.  
       For Single Employee: http://localhost:8080/employees/{employeeId}/tax-deduction  
       For All Employees: http://localhost:8080/employees/tax-deductions 
-------------------------------------------------------------------------------------------------  

1. Please go through to http://localhost:8080/swagger-ui/index.html for API Documentation  
2. Please use http://localhost:8080/h2-console for H2 Database  
   with URL: jdbc:h2:file:./data/imaginnovate  
   User Name: imaginnovate
   password: imaginnovate
Note: Used H2 File Database, so the data will be retained even after server restarted.
