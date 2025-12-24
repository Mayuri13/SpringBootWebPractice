package com.example.springbootwebpractise.SpringBootWebPractice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
public class DepartmentDTO {

    private Long id;

    @NotNull(message = "Department title cannot be empty")
    private String title;

    private Boolean isActive;
}
