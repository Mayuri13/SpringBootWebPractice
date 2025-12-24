package com.example.springbootwebpractise.SpringBootWebPractice.controllers;

import com.example.springbootwebpractise.SpringBootWebPractice.dto.DepartmentDTO;
import com.example.springbootwebpractise.SpringBootWebPractice.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> allDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(name = "departmentId") Long departmentId){
        return ResponseEntity.ok(departmentService.getDepartment(departmentId));
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createNewDepartment(@RequestBody @Valid DepartmentDTO newDepartment) {
        DepartmentDTO savedDepartment = departmentService.createDepartment(newDepartment);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@PathVariable Long departmentId, @RequestBody @Valid DepartmentDTO department) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId, department));
    }

    @DeleteMapping(path = "/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long departmentId){
        boolean gotDeleted = departmentService.deleteDepartmentById(departmentId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }
}
