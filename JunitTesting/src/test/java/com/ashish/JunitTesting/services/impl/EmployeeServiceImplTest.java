package com.ashish.JunitTesting.services.impl;

import com.ashish.JunitTesting.TestContainerConfiguration;
import com.ashish.JunitTesting.dto.EmployeeDto;
import com.ashish.JunitTesting.entities.Employee;
import com.ashish.JunitTesting.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.verification.Only;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.http.MediaTypeAssert;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;


    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1L)
                .email("ashishkumarr0856@gmail.com")
                .name("ashish kumar")
                .salary(300L)
                .build();

        mockEmployeeDto = modelMapper.map( mockEmployee, EmployeeDto.class);
    }


    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {

         //assign
//        Long id =1L;
//        Employee mockEmployee = Employee.builder()
//                .id(id)
//                .email("ashishkumarr0856@gmail.com")
//                .name("ashish kumar")
//                .salary(300L)
//                .build();

        Long id = mockEmployee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee)); // strubbing


        //act
      EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        //assert
        assertThat(employeeDto.getId()).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());

        verify(employeeRepository, only()).findById(id);
    }


    @Test
    Void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException() {


    }
    @Test
    void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployeeDto() {

        //assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
        //act

        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

        //assert
        assertThat(employeeDto.getId()).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

//        verify(employeeRepository).save(any(Employee.class));
        verify(employeeRepository).save(employeeArgumentCaptor.capture());

        Employee employee = employeeArgumentCaptor.getValue();
        assertThat(employee.getEmail()).isEqualTo(mockEmployeeDto.getEmail());



    }

}