package com.example.springbootwebpractise.SpringBootWebPractice.repositories;

import com.example.springbootwebpractise.SpringBootWebPractice.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {}
