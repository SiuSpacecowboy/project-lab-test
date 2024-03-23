package com.example.labtestproject.services;

import com.example.labtestproject.dto.LimitDto;
import com.example.labtestproject.dto.TransactionDto;
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
public class TransactionDtoService {

    private final TransactionDtoRepository repository;
    private final LimitDtoService limitDtoService;
    private final EntitiesValidator<TransactionDto> transValidator;
    private final AccountValidator accValidator;

    @Autowired
    public TransactionDtoService(TransactionDtoRepository repository, LimitDtoService limitDtoService,
                                 EntitiesValidator<TransactionDto> transValidator, AccountValidator accValidator) {
        this.repository = repository;
        this.limitDtoService = limitDtoService;
        this.transValidator = transValidator;
        this.accValidator = accValidator;
    }

    /** Метод находит все транзакции, принадлежащие аккаунту. */
    public ResponseEntity<?> findTransByAccId(long id) {
        List<TransactionDto> resList = repository.findByAccountId(id);
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
    public ResponseEntity<String> saveTransactionAndUpdateLimits(TransactionDto trans, long id) {
        ResponseEntity<String> resp = null;
        if (transValidator.validate(trans) && accValidator.checkAccountId(id)) {
            trans.setAccountId(id);
            repository.save(trans);
            Optional<LimitDto> lim = limitDtoService.findLatestLimitInCategory(id, trans.getExpenseCategory());
            if (lim.isEmpty()) {
                LimitDto newLim = new LimitDto(new BigDecimal("1000"), new BigDecimal("1000"),
                        "USD", trans.getExpenseCategory(), id);
                limitDtoService.createLimSumIfEmptyAndUpdateLimitRem(newLim, trans);
            } else {
                limitDtoService.createLimSumIfEmptyAndUpdateLimitRem(lim.get(), trans);
            }
            resp = ResponseEntity.status(HttpStatus.CREATED)
                    .body("The transaction is completed and successfully saved.");
        }
        return resp;
    }
}
