package com.example.labtestproject.controllers;

import com.example.labtestproject.dto.TransactionDto;
import com.example.labtestproject.services.TransactionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Контроллер, обрабатывающий и демонстрирующий транзакции,
 * которые принадлежат определенному аккаунту. */
@RestController
@RequestMapping(value = "api/v1/acc/{id}/transactions")
public class TransactionController {

    private final TransactionDtoService transactionService;

    @Autowired
    public TransactionController(TransactionDtoService transactionDtoService) {
        this.transactionService = transactionDtoService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTransById(@PathVariable("id") int id) {
        return transactionService.findTransByAccId(id);
    }

    @PostMapping("/new-trans")
    public ResponseEntity<String> sendNewTransaction(@PathVariable("id") int id, @RequestBody TransactionDto trans) {
            return transactionService.saveTransactionAndUpdateLimits(trans, id);
    }
}
