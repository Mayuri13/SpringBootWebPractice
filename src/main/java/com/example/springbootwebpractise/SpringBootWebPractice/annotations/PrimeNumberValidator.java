package com.example.springbootwebpractise.SpringBootWebPractice.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<PrimeNumberValidation, Integer> {

    @Override
    public boolean isValid(Integer inputNumber,
                           ConstraintValidatorContext constraintValidatorContext){
        if(inputNumber == 0 || inputNumber == 1) return false;

        int i = 2;
        while(i <= Math.sqrt(inputNumber)){
            if(inputNumber % i == 0) return false;
            i++;
        }
        return true;
    }
}
