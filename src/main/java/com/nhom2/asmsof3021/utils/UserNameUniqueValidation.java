package com.nhom2.asmsof3021.utils;

import com.nhom2.asmsof3021.repository.UserRepository;
import com.nhom2.asmsof3021.validation.UsernameUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNameUniqueValidation implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsByUsername(email);
    }
}
