package com.example.labtestproject.validators;

import com.example.labtestproject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/** Класс, проверяющий на ошибку несуществующего аккаунта. */
@Component
public class AccountValidator {

    private final AccountService service;

    @Autowired
    public AccountValidator(AccountService service) {
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
