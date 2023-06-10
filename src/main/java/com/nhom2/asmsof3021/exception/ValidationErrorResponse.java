package com.nhom2.asmsof3021.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    // ...
}


