package com.nhom2.asmsof3021.validation.annotations;

import com.nhom2.asmsof3021.validation.constraint.PhoneNumberConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumberConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "Số điện thoại không đúng định dạng";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
