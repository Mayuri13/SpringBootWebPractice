package com.example.springbootwebpractise.SpringBootWebPractice.dto;

import com.example.springbootwebpractise.SpringBootWebPractice.annotations.EmployeeRoleValidation;
import com.example.springbootwebpractise.SpringBootWebPractice.annotations.PrimeNumberValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotNull(message = "Required field in Employee: name")
    @Size(min = 3, max = 10, message = "number of characters in name should be between 3 to 10")
    private String name;

    @Email(message = "Email should be a valid email")
    private String email;

    @Max(value = 80, message = "Age cannot be greater than 80")
    @Min(value = 18, message = "Age cannot be lesser than 18")
    private Integer age;

    //This annotation restricts that role will only have value as ADMIN or USER
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of Employee can be USER or ADMIN")
    @EmployeeRoleValidation
    @NotNull(message = "Role of employee cannot be blank")
    private String role; //ADMIN, USER

    @NotNull(message = "Salary of employee should be not null")
    @Positive(message = "Salary should be positive")
    private Integer salary;

    @PrimeNumberValidation
    private Integer favPrimeNumber;

    @PastOrPresent(message = "Date of joining field of employee cannot be in future")
    private LocalDate dateOfJoining;
    private Boolean isActive;
}
