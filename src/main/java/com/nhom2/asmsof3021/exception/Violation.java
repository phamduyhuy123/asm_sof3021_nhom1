package com.nhom2.asmsof3021.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public record Violation(String fieldName, String message) {

    // ...
}
