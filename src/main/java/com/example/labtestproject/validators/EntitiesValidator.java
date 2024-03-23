package com.example.labtestproject.validators;

import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/** Класс, валидирующий объект любого типа, при неверных значениях выкидывает ошибку. */
@Component
public class EntitiesValidator<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public boolean validate(T entityToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(entityToValidate);
        Set<String> res = Collections.emptySet();
        if (!violations.isEmpty()) {
            var messages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ValidationException(String.join("\n", messages));
        }
        return true;
    }
}
