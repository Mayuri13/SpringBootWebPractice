package com.example.springbootwebpractise.SpringBootWebPractice.controllers;
import java.time.LocalDate;

import com.example.springbootwebpractise.SpringBootWebPractice.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        return new EmployeeDTO(id, "Anuj", "anuj@gmail.com", 24, LocalDate.of(2025,12,6), true);
    }

    @GetMapping
    public String getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String sortBy) {
        return "Hi age " + age + " " + sortBy;
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO newEmployee) {
        newEmployee.setId(20L);
        return newEmployee;
    }

    @PutMapping
    public  String updateEmployee() {
        return "Hello from put";
    }
}
