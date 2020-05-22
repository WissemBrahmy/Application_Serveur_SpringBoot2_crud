package com.example.test.spring.crud.repository;


import com.example.test.spring.crud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called EmployeeRepository
//CRUD refers Create, Read, Update, Delete

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
