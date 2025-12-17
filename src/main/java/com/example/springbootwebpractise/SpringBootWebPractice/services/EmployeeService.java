package com.example.springbootwebpractise.SpringBootWebPractice.services;

import com.example.springbootwebpractise.SpringBootWebPractice.dto.EmployeeDTO;
import com.example.springbootwebpractise.SpringBootWebPractice.entities.EmployeeEntity;
import com.example.springbootwebpractise.SpringBootWebPractice.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeDTO getEmployeeById(Long id){
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO newEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(newEmployee, EmployeeEntity.class);
        EmployeeEntity saveEmployeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(saveEmployeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        EmployeeEntity entity = employeeRepository.findById(employeeId)
                .map(existing -> {
                    // update existing entity fields (map into existing to preserve DB-managed fields)
                    modelMapper.map(employeeDTO, existing);
                    existing.setId(employeeId); // redundant but explicit
                    return employeeRepository.save(existing);
                })
                .orElseGet(() -> {
                    // create new entity with the provided id
                    EmployeeEntity toSave = modelMapper.map(employeeDTO, EmployeeEntity.class);
                    toSave.setId(employeeId);
                    return employeeRepository.save(toSave);
                });

        return modelMapper.map(entity, EmployeeDTO.class);
    }

    public boolean isExistsByEmployeeId(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        boolean exists = isExistsByEmployeeId(employeeId);
        if(!exists) return false;

        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        boolean exists = isExistsByEmployeeId(employeeId);
        if(!exists) return null;

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((key, values) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, values);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
