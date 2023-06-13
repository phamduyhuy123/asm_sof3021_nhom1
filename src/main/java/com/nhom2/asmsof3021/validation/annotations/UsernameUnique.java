package com.nhom2.asmsof3021.validation.annotations;

import com.nhom2.asmsof3021.validation.constraint.UserNameUniqueValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UserNameUniqueValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {
    String message() default "Username này đã tồn tại";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
