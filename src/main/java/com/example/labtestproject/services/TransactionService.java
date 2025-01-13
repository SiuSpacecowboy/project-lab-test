package com.example.labtestproject.services;

import com.example.labtestproject.entity.LimitEntity;
import com.example.labtestproject.entity.TransactionEntity;
import com.example.labtestproject.repositories.TransactionDtoRepository;
import com.example.labtestproject.validators.AccountValidator;
import com.example.labtestproject.validators.EntitiesValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/** Класс, добавляющий транзакции в базу данных. */
@Service
public class TransactionService {

    private final TransactionDtoRepository repository;
    private final LimitService limitService;
    private final EntitiesValidator<TransactionEntity> transValidator;
    private final AccountValidator accValidator;

    @Autowired
    public TransactionService(TransactionDtoRepository repository, LimitService limitService,
                              EntitiesValidator<TransactionEntity> transValidator, AccountValidator accValidator) {
        this.repository = repository;
        this.limitService = limitService;
        this.transValidator = transValidator;
        this.accValidator = accValidator;
    }

    /** Метод находит все транзакции, принадлежащие аккаунту. */
    public ResponseEntity<?> findTransByAccId(long id) {
        List<TransactionEntity> resList = repository.findByAccountId(id);
        if (accValidator.checkAccountId(id)) {
            if (resList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("No transactions have been made yet.");
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(resList);
    }

    /** Метод, который сохраняет транзакцию и вызывает метод,
     * перезаписывающий остаток от лимита и устанавливающий флаг. */
    @Transactional
    public ResponseEntity<String> saveTransactionAndUpdateLimits(TransactionEntity trans, long id) {
        ResponseEntity<String> resp = null;
        if (transValidator.validate(trans) && accValidator.checkAccountId(id)) {
            trans.setAccountId(id);
            repository.save(trans);
            Optional<LimitEntity> lim = limitService.findLatestLimitInCategory(id, trans.getExpenseCategory());
            if (lim.isEmpty()) {
                LimitEntity newLim = new LimitEntity(new BigDecimal("1000"), new BigDecimal("1000"),
                        "USD", trans.getExpenseCategory(), id);
                limitService.createLimSumIfEmptyAndUpdateLimitRem(newLim, trans);
            } else {
                limitService.createLimSumIfEmptyAndUpdateLimitRem(lim.get(), trans);
            }
            resp = ResponseEntity.status(HttpStatus.CREATED)
                    .body("The transaction is completed and successfully saved.");
        }
        return resp;
    }
}
