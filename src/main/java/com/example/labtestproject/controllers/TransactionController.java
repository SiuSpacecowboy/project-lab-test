package com.example.labtestproject.controllers;

import com.example.labtestproject.entity.TransactionEntity;
import com.example.labtestproject.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Контроллер, обрабатывающий и демонстрирующий транзакции,
 * которые принадлежат определенному аккаунту. */
@RestController
@RequestMapping(value = "api/v1/acc/{id}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTransById(@PathVariable("id") int id) {
        return transactionService.findTransByAccId(id);
    }

    @PostMapping("/new-trans")
    public ResponseEntity<String> sendNewTransaction(@PathVariable("id") int id, @RequestBody TransactionEntity trans) {
            return transactionService.saveTransactionAndUpdateLimits(trans, id);
    }
}
