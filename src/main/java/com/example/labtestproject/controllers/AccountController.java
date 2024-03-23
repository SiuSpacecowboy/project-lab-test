package com.example.labtestproject.controllers;

import com.example.labtestproject.dto.AccountDto;
import com.example.labtestproject.services.AccountDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Контроллер, обрабатывающий взаимодействия с аккаунтом. */
@RestController
@RequestMapping(value = "api/v1/acc")
public class AccountController {

    private final AccountDtoService service;

    @Autowired
    public AccountController(AccountDtoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> showAllAccounts() {
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
