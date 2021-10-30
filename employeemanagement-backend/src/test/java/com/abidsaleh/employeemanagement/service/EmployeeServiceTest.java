package com.abidsaleh.employeemanagement.service;

import com.abidsaleh.employeemanagement.exception.UserNotFoundException;
import com.abidsaleh.employeemanagement.model.Employee;
import com.abidsaleh.employeemanagement.repo.EmployeeRepo;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    private static final Long EMPLOYEE_ID = 2L;
    private static final String EMPLOYEE_CODE = "test_emp_code";
    private static Employee employee;
    private static List<Employee> employees;

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepo employeeRepo;

    @BeforeClass
    public static void setup(){
        employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setEmployeeCode(EMPLOYEE_CODE);
        employees= new ArrayList<>();
        employees.add(employee);
    }

    @Test
    public void find_all_employees_test(){
        //when then
        when(employeeRepo.findAll()).thenReturn(employees);

        //assertion
        List<Employee> foundEmployees = employeeService.findAllEmployees();

        assertEquals(foundEmployees.get(0).getId(), EMPLOYEE_ID);
        verify(employeeRepo, times(1)).findAll();
        verifyNoMoreInteractions(employeeRepo);
    }

    @Test
    public void find_employee_by_id_success_test(){
        //when then
        when(employeeRepo.findEmployeeById(anyLong())).thenReturn(java.util.Optional.ofNullable(employee));

        //assertion
        Employee foundEmployee = employeeService.findEmployeeById(EMPLOYEE_ID);

        assertEquals(foundEmployee.getId(), EMPLOYEE_ID);
        verify(employeeRepo, times(1)).findEmployeeById(anyLong());
        verifyNoMoreInteractions(employeeRepo);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void find_employee_by_id_not_found_test(){

        //expectation
        exception.expect(UserNotFoundException.class);
        exception.expectMessage("user by id "+ EMPLOYEE_ID +" was not found");

        //when then
        when(employeeRepo.findEmployeeById(anyLong())).thenReturn(Optional.empty());

        //assertion
        Employee nonExistentEmployee = employeeService.findEmployeeById(EMPLOYEE_ID);
        verify(employeeRepo, times(0)).findEmployeeById(anyLong());
        verifyNoMoreInteractions(employeeRepo);
    }


    @Test
    public void add_employee_test(){

        //when then
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        //assertion

        Employee addedEmployee = employeeService.addEmployee(employee);

        assertEquals(addedEmployee.getId(), EMPLOYEE_ID);
        verify(employeeRepo, times(1)).save(any(Employee.class));
        verifyNoMoreInteractions(employeeRepo);
    }

    @Test
    public void update_employee_test(){

        //when then
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        //assertion
        Employee updateEmployee= employeeService.updateEmployee(employee);

        assertEquals(updateEmployee.getId(), EMPLOYEE_ID);
        verify(employeeRepo, times(1)).save(any(Employee.class));
        verifyNoMoreInteractions(employeeRepo);
    }

    @Test
    public void delete_employee_by_id_test(){
        //when then
        doNothing().when(employeeRepo).deleteEmployeeById(anyLong());
        //assertion
        employeeService.deleteEmployee(EMPLOYEE_ID);

        verify(employeeRepo, times(1)).deleteEmployeeById(anyLong());
        verifyNoMoreInteractions(employeeRepo);
    }

}
