package com.example.labtestproject.services;

import com.example.labtestproject.dto.FlagDto;
import com.example.labtestproject.dto.LimitDto;
import com.example.labtestproject.dto.TransactionDto;
import com.example.labtestproject.repositories.CourseTranslationDtoRepository;
import com.example.labtestproject.repositories.LimitDtoRepository;
import com.example.labtestproject.validators.AccountValidator;
import com.example.labtestproject.validators.EntitiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/** Класс направленный на манипуляции с лимитами. */
@Service
public class LimitDtoService {

    private final LimitDtoRepository repository;
    private final CourseTranslationDtoRepository trRepository;
    private final EntitiesValidator<LimitDto> entityValidator;
    private final AccountValidator accValidator;

    @Autowired
    public LimitDtoService(LimitDtoRepository repository, CourseTranslationDtoRepository trRepository,
                           EntitiesValidator<LimitDto> entityValidator, AccountValidator accValidator) {
        this.repository = repository;
        this.trRepository = trRepository;
        this.entityValidator = entityValidator;
        this.accValidator = accValidator;
    }

    /** Метод находит все лимиты, принадлежащие аккаунту. */
    public ResponseEntity<?> findLimByAccId(long id) {
        List<LimitDto> resList = repository.findByAccountId(id);
        if (accValidator.checkAccountId(id)) {
            if (resList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("No limits have been set yet.");
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(resList);
    }

    /** Метод, который находит актуальный действующий лимит по категории. */
    public Optional<LimitDto> findLatestLimitInCategory(long id, String category) {
        return repository.findTopByAccIdAndCategoryOrdByIdDesc(id, category);
    }

    /** Метод, по запросу клиента возвращающий список транзакций, превысивших лимит, с
    указанием лимита, который был превышен. */
    public ResponseEntity<List<Object[]>> findTransWithFlagsByAccId(long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(repository.findTransactionsWithFlagsAndLimits(id));
    }

    /** Метод, который сохраняет новый лимит аккаунта на транзакции. */
    public ResponseEntity<String> saveLimitInAcc(LimitDto data, long id) {
        ResponseEntity<String> resp = null;
        if (entityValidator.validate(data) && accValidator.checkAccountId(id)) {
            Optional<LimitDto> lim = repository.findTopByAccIdAndCategoryOrdByIdDesc(id, data.getExpenseCategory());
            if (lim.isEmpty()) {
                repository.save(new LimitDto(data.getLimitSum(), data.getLimitSum(),
                        "USD", data.getExpenseCategory(), id));
            } else {
                BigDecimal newLimRem = data.getLimitSum()
                        .subtract(lim.get().getLimitSum()
                                .subtract(lim.get().getLimitRem()));
                repository.save(new LimitDto(data.getLimitSum(), newLimRem,
                        "USD", data.getExpenseCategory(), id));
            }
            resp = ResponseEntity.status(HttpStatus.CREATED)
                    .body("The limit has been successfully set.");
        }
        return resp;
    }

    /** Метод, принимает транзакцию и прилежащий к ней лимит,
     * записывает новое значение в остаток и устанавливает флаг транзакции. */
    public void createLimSumIfEmptyAndUpdateLimitRem(LimitDto lim, TransactionDto trans) {
        BigDecimal subSum = courseTranslation(trans.getSum(), trans.getCurrencyShortName());
        BigDecimal newLimitRem = lim.getLimitRem().subtract(subSum);
        lim.setLimitRem(newLimitRem);
        repository.save(lim);
        if (newLimitRem.doubleValue() >= 0) {
            trans.setFlagDto(new FlagDto("false", trans));
        } else {
            trans.setFlagDto(new FlagDto("true", trans));
        }
    }

    /** Метод, который переводит валюту в доллары на основе курса из таблицы. */
    public BigDecimal courseTranslation(BigDecimal subSum, String currencyShortname) {
        switch (currencyShortname.toLowerCase()) {
            case "rub" -> {
                double course = trRepository.findById(1L)
                        .get().getCourseUsdRub();
                subSum = new BigDecimal(subSum.doubleValue() / course);
            }
            case "kzt" -> {
                double course = trRepository.findById(1L)
                        .get().getCourseUsdKzt();
                subSum = new BigDecimal(subSum.doubleValue() / course);
            }
        }
        return subSum;
    }
}
