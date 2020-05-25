package com.example.test.spring.crud.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.spring.crud.exception.ResourceNotFoundException;
import com.example.test.spring.crud.model.Employee;
import com.example.test.spring.crud.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

/* To enable CORS on the server */
@CrossOrigin(origins = "*") // 
@RestController
@RequestMapping("/api/test") // This means URL's start with /api/test (after Application path)
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
    
        Employee employee = employeeRepository.findById(employeeId)
               .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }
  
    @GetMapping(value = "employees/search/{department}")
    public List<Employee> findByDepartment(@PathVariable String department) {
   
      return employeeRepository.findByDepartment(department);
      
    }
    
    @PostMapping("/employees") // Map ONLY POST Requests
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
       
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
         
    Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    	employee.setDepartment(employeeDetails.getDepartment());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setSex(employeeDetails.getSex());
        employee.setBirthDate(employeeDetails.getBirthDate());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
       
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
        
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Employee deleted", Boolean.TRUE);
        return response;
    }
}