package com.example.labtestproject.controllers;

import com.example.labtestproject.entity.AccountEntity;
import com.example.labtestproject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Контроллер, обрабатывающий взаимодействия с аккаунтом. */
@RestController
@RequestMapping(value = "api/v1/acc")
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccountEntity>> showAllAccounts() {
        return service.getAccounts();
    }

    @PostMapping
    public ResponseEntity<String> addAccount() {
        return service.saveNewBill();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showAccount(@PathVariable long id) {
        return service.findAccountByIdForController(id);
    }

}
