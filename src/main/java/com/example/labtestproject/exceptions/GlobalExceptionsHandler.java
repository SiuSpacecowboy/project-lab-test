package com.example.labtestproject.exceptions;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;


/**
 * Класс-обработчик глобальных исключений для обработки исключений в приложении.
 * Возвращает ResponseEntity<?>.
 */
@RestControllerAdvice
public class GlobalExceptionsHandler {

    /**
     * Метод обрабатывает ошибку неверного url адреса.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Wrong url address");
    }

    /**
     * Метод обрабатывает ошибку неверного ввода данных при отправлении их на сервер пользователем.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    /**
     * Метод обрабатывает ошибку несуществующего аккаунта(неверный id в запросе url).
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Account with id = " +e.getMessage() + " doesn't exist");
    }
}
