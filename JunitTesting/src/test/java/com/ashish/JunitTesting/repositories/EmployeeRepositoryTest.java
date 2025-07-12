package com.ashish.JunitTesting.repositories;

import com.ashish.JunitTesting.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .name("Ashish Kumar")
                .email("kumar@ashish.com")
                .salary(100L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {

        // Arrange,given
        employeeRepository.save(employee);

        // Act ,When
        List<Employee> employeeList=employeeRepository.findByEmail(employee.getEmail());

        //Assert, Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
    }



    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmployeeList() {
        // Given
          String email = "notfound@email.com";
        //When
        List<Employee> employeeList=employeeRepository.findByEmail(email);

//       Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
    }
}