package com.example.springbootwebpractise.SpringBootWebPractice.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

    private HttpStatus status;
    private String message;



}
