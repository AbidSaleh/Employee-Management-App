package com.abidsaleh.employeemanagement.repo;

import com.abidsaleh.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    //spring creates a query by understanding the method using naming convention
    //delete+ classname + ById
    //it is called query method
    @Transactional
    void deleteEmployeeById(Long id);

    //query method
    Optional<Employee> findEmployeeById(Long id);
}
