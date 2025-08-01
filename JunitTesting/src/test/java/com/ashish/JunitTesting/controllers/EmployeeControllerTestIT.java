package com.ashish.JunitTesting.controllers;

import com.ashish.JunitTesting.TestContainerConfiguration;
import com.ashish.JunitTesting.dto.EmployeeDto;
import com.ashish.JunitTesting.entities.Employee;
import com.ashish.JunitTesting.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class EmployeeControllerTestIT {

    @Autowired
    private WebTestClient webClient;

    private Employee employee;
    private EmployeeDto testEmployeeDto;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .email("ashishkumarr0856@gmail.com")
                .name("ashish patel")
                .salary(300L)
                .build();
        testEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .email("ashishkumarr0856@gmail.com")
                .name("ashish patel")
                .salary(300L)
                .build();
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success() {
        Employee savedEmployee = employeeRepository.save(employee);
        webTestClient.get()
                .uri("/employees/{id}" + savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(testEmployeeDto);
//                .value(employeeDto -> {
//                    assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
//                    assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
//                });

    }
    @Test
    void testGetEmployeeById_Failure() {
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(employee);
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExists_thenCreateEmployee() {
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

}