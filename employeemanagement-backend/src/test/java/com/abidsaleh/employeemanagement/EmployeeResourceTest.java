package com.abidsaleh.employeemanagement;

import com.abidsaleh.employeemanagement.model.Employee;
import com.abidsaleh.employeemanagement.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeResourceTest {

    private static final Long EMPLOYEE_ID = 2L;
    private static final String EMPLOYEE_CODE = "test_emp_code";
    private static Employee employee;
    private static List<Employee> employees;

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private EmployeeService employeeService;

    @BeforeClass
    public static void setup(){
        employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setEmployeeCode(EMPLOYEE_CODE);
        employees= new ArrayList<>();
        employees.add(employee);
    }

    @Test
    public void get_all_employees_should_return_200_when_valid_request() throws Exception{

        //when-then
        when(employeeService.findAllEmployees())
                .thenReturn(employees);

        //assertion
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$[0].employeeCode").value(EMPLOYEE_CODE));

        //verification
        verify(employeeService,times(1)).findAllEmployees();
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void get_employee_by_id_should_return_200_when_employee_isExist() throws Exception{

        //when-then
        when(employeeService.findEmployeeById(EMPLOYEE_ID)).thenReturn(employee);

        //assertion
        mockMvc.perform(get("/employees/{id}", EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$.employeeCode").value(EMPLOYEE_CODE));

        //verification
        verify(employeeService,times(1)).findEmployeeById(anyLong());
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void add_employee_should_return_201_when_employee_is_added() throws Exception{

        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(employee);
        //when-then
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        //assertion
        mockMvc.perform(post("/employees/").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$.employeeCode").value(EMPLOYEE_CODE));

        //verification
        verify(employeeService,times(1)).addEmployee(any(Employee.class));
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void update_employee_should_return_200_when_employee_is_updated() throws Exception{

        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(employee);

        //when-then
        when(employeeService.updateEmployee(any(Employee.class))).thenReturn(employee);

        //assertion
        mockMvc.perform(put("/employees/").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$.employeeCode").value(EMPLOYEE_CODE));

        //verification
        verify(employeeService,times(1)).updateEmployee(any(Employee.class));
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void delete_employee_by_id_should_return_200_when_employee_is_deleted() throws Exception{

        //when-then
        Mockito.doNothing().when(employeeService).deleteEmployee(anyLong());
        //assertion
        mockMvc.perform(delete("/employees/{id}", EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //verification
        verify(employeeService,times(1)).deleteEmployee(anyLong());
        verifyNoMoreInteractions(employeeService);

    }

}
