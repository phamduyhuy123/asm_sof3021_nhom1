package com.nhom2.asmsof3021.validation;

import com.nhom2.asmsof3021.utils.PasswordConstraintValidator;
import com.nhom2.asmsof3021.utils.UniqueEmailValidator;
import com.nhom2.asmsof3021.utils.UserNameUniqueValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UserNameUniqueValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {
    String message() default "Username này đã tồn tại";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
