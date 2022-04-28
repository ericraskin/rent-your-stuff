package com.paslists.rys.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationVerification<T> {

    @Autowired
    Validator validator;

    public List<ValidationResult<T>> validate(T object) {
        return validator.validate(object, Default.class)
                .stream().map(ValidationResult::new)
                .collect(Collectors.toList());
    }

    public ValidationResult<T> validateFirst(T object) {
        return validate(object).get(0);
    }

    public String validationMessage(String errorType) {
        return "{javax.validation.constraints." + errorType + ".message}";
    }

    public static class ValidationResult<T> {
        private final ConstraintViolation<T> violation;

        public ValidationResult(ConstraintViolation<T> violation) {
            this.violation = violation;
        }

        public String getAttribute() {
            return violation.getPropertyPath().toString();
        }

        public String getErrorType() {
            return violation.getMessageTemplate();
        }
    }
}
