package com.example.labtestproject.services;

import com.example.labtestproject.dto.AccountDto;
import com.example.labtestproject.repositories.AccountDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Класс-посредник между сервером и таблицей базы данных. */
@Service
public class AccountDtoService {

    private final AccountDtoRepository repository;

    @Autowired
    public AccountDtoService(AccountDtoRepository repository) {
        this.repository = repository;
    }

    /** Метод получает из таблицы все аккаунты. */
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(repository.findAll());
    }

    /** Метод получает конкретный аккаунт по id,
     * побочный метод для проверки на существование аккаунта. */
    public Optional<AccountDto> findAccountById(long id) {
        return repository.findById(id);
    }

    /** Специальный метод получения аккаунта по id, для контроллера. */
    public ResponseEntity<?> findAccountByIdForController(long id) {
        Optional<AccountDto> acc = repository.findById(id);
        ResponseEntity<?> resp;
        if (acc.isEmpty()) {
            resp = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account with id = " + id + " doesn't exist");
        } else {
            resp = ResponseEntity.status(HttpStatus.OK)
                    .body(acc.get());
        }
        return resp;
    }

    /** Метод для сохранения нового аккаунта, с автоматической генерацией счета. */
    public ResponseEntity<String> saveNewBill() {
        Optional<Integer> maxAcc = repository.findMaxId();
        if (maxAcc.isEmpty()) {
            repository.save(new AccountDto("0000000001"));
        } else {
            repository.save(new AccountDto(String.format("%010d", maxAcc.get() + 1)));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("New account has been created");
    }
}
