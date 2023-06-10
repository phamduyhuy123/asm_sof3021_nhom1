package com.nhom2.asmsof3021.utils;

import com.nhom2.asmsof3021.validation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.passay.*;

import java.util.List;

@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    private final PasswordValidator validator;
    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {


        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(", ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
