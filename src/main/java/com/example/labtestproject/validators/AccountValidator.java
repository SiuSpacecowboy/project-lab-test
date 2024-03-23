package com.example.labtestproject.validators;

import com.example.labtestproject.services.AccountDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/** Класс, проверяющий на ошибку несуществующего аккаунта. */
@Component
public class AccountValidator {

    private final AccountDtoService service;

    @Autowired
    public AccountValidator(AccountDtoService service) {
        this.service = service;
    }

    public boolean checkAccountId(long id) {
        if (service.findAccountById(id).isEmpty()) {
            throw new NoSuchElementException(String.valueOf(id));
        } else {
            return true;
        }
    }
}
