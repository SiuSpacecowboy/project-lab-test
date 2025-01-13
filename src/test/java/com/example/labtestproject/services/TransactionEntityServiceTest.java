package com.example.labtestproject.services;

import com.example.labtestproject.entity.LimitEntity;
import com.example.labtestproject.entity.TransactionEntity;
import com.example.labtestproject.repositories.TransactionDtoRepository;
import com.example.labtestproject.validators.AccountValidator;
import com.example.labtestproject.validators.EntitiesValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionEntityServiceTest {

    @Mock
    TransactionDtoRepository repository;

    @Mock
    AccountValidator accValidator;

    @Mock
    EntitiesValidator<TransactionEntity> transValidator;

    @Mock
    LimitService limitService;

    @InjectMocks
    TransactionService service;

    @Test
    public void testSaveTransactionAnd_Success() {

        // Arrange
        TransactionEntity trans = new TransactionEntity();
        long accountId = 1;
        when(transValidator.validate(trans)).thenReturn(true);
        when(accValidator.checkAccountId(accountId)).thenReturn(true);
        LimitEntity mockLimitEntity = new LimitEntity(new BigDecimal("1000"), new BigDecimal("1000"),
                "USD", "category", accountId);
        when(limitService.findLatestLimitInCategory(accountId, trans.getExpenseCategory())).thenReturn(Optional.of(mockLimitEntity));

        // Act
        ResponseEntity<String> result = service.saveTransactionAndUpdateLimits(trans, accountId);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo("The transaction is completed and successfully saved.");
        verify(repository).save(trans);
        verify(limitService).createLimSumIfEmptyAndUpdateLimitRem(mockLimitEntity, trans);
    }


    @Test
    void findTransByAccIdWhenArrNotEmptyTest() {

        // Arrange
        long accountId = 123;
        List<TransactionEntity> mockTransactions = Arrays.asList(
                new TransactionEntity("1", "2", "KZT",
                        new BigDecimal(500), "product"),
                new TransactionEntity("2", "3", "KZT",
                        new BigDecimal(500), "product")
        );

        when(accValidator.checkAccountId(accountId)).thenReturn(true);
        when(repository.findByAccountId(accountId)).thenReturn(mockTransactions);

        // Act
        ResponseEntity<?> response = service.findTransByAccId(accountId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTransactions, response.getBody());
    }

    @Test
    void findTransByAccIdWhenArrEmptyTest() {

        // Arrange
        long accountId = 123;
        List<TransactionEntity> mockTransactions = Collections.emptyList();

        when(accValidator.checkAccountId(accountId)).thenReturn(true);
        when(repository.findByAccountId(accountId)).thenReturn(mockTransactions);

        // Act
        ResponseEntity<?> response = service.findTransByAccId(accountId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No transactions have been made yet.", response.getBody());
    }
}