package com.example.springbootwebpractise.SpringBootWebPractice.services;

import com.example.springbootwebpractise.SpringBootWebPractice.dto.DepartmentDTO;
import com.example.springbootwebpractise.SpringBootWebPractice.dto.EmployeeDTO;
import com.example.springbootwebpractise.SpringBootWebPractice.entities.DepartmentEntity;
import com.example.springbootwebpractise.SpringBootWebPractice.entities.EmployeeEntity;
import com.example.springbootwebpractise.SpringBootWebPractice.exceptions.ResourceNotFoundException;
import com.example.springbootwebpractise.SpringBootWebPractice.repositories.DepartmentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentsRepository;
    private final ModelMapper modelMapper;

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> allDepartments = departmentsRepository.findAll();
        return allDepartments
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(@Valid DepartmentDTO newDepartment) {
        DepartmentEntity toSaveDepartment = modelMapper.map(newDepartment, DepartmentEntity.class);
        DepartmentEntity savedDepartment = departmentsRepository.save(toSaveDepartment);
        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(Long id, @Valid DepartmentDTO departmentDTO) {
        isExistsById(id);
        DepartmentEntity entity = departmentsRepository.findById(id)
                .map(existing -> {
                    // update existing entity fields (map into existing to preserve DB-managed fields)
                    modelMapper.map(departmentDTO, existing);
                    existing.setId(id); // redundant but explicit
                    return departmentsRepository.save(existing);
                })
                .orElseGet(() -> {
                    // create new entity with the provided id
                    DepartmentEntity toSave = modelMapper.map(departmentDTO, DepartmentEntity.class);
                    toSave.setId(id);
                    return departmentsRepository.save(toSave);
                });

        return modelMapper.map(entity, DepartmentDTO.class);
    }

    public void isExistsById(Long id){
        boolean exists = departmentsRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Department not found with id: " + id);
    }

    public DepartmentDTO getDepartment(Long departmentId) {
        isExistsById(departmentId);
        DepartmentEntity savedDepartment = departmentsRepository.findById(departmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found " + departmentId));
        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    public boolean deleteDepartmentById(Long departmentId) {
        isExistsById(departmentId);
        departmentsRepository.deleteById(departmentId);
        return true;
    }
}
