package com.paslists.rys.test_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class Validations<T> {

    @Autowired
    Validator validator;

    public List<ValidationResult<T>> validate(T entity) {
        return validator.validate(entity, Default.class)
                .stream().map(ValidationResult::new)
                .collect(Collectors.toList());
    }

    public String validationMessage(String errorType) {
        return "{javax.validation.constraints." + errorType + ".message}";
    }

    public void assertNoViolations(T entity) {
        List<Validations.ValidationResult<T>> violations = validate(entity);

        assertThat(violations).hasSize(0);
    }

    public void assertExactlyOneViolationWith(T entity, String attribute, String errorType) {
        List<Validations.ValidationResult<T>> violations = validate(entity);

        // then

        assertThat(violations).hasSize(1);

        Validations.ValidationResult<T> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo(attribute);

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationMessage(errorType));
    }

    public void assertOneViolationWith(T entity, String attribute, String errorType) {
        List<Validations.ValidationResult<T>> violations = validate(entity);

        assertThat(violations)
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(validationResult -> validationResult.matches(attribute, validationMessage(errorType)));
    }

    public void assertOneViolationWith(T entity, String errorType) {
        List<Validations.ValidationResult<T>> violations = validate(entity);

        assertThat(violations)
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(validationResult -> validationResult.matches(validationMessage(errorType)));
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

        private boolean matches(String attribute, String errorType) {
            return this.getAttribute().equals(attribute) && this.getErrorType().equals(errorType);
        }

        private boolean matches(String errorType) {
            return this.getErrorType().equals(errorType);
        }
    }
}
