package com.example.labtestproject.services;

import com.example.labtestproject.controllers.LimitController;
import com.example.labtestproject.dto.LimitDto;
import com.example.labtestproject.dto.TransactionDto;
import com.example.labtestproject.repositories.CourseTranslationDtoRepository;
import com.example.labtestproject.repositories.LimitDtoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LimitDtoServiceTest {

    @Mock
    LimitDtoRepository repository;

    @Mock
    AccountValidator accValidator;

    @Mock
    EntitiesValidator<LimitDto> entityValidator;

    @InjectMocks
    LimitDtoService service;



    @Test
    public void findLimByAccId_WhenValidAccountId_ExpectListReturned() {

        // Arrange
        long accountId = 1L;
        List<LimitDto> limitList = Arrays.asList(new LimitDto(), new LimitDto());
        when(repository.findByAccountId(accountId)).thenReturn(limitList);
        when(accValidator.checkAccountId(accountId)).thenReturn(true);

        // Act
        ResponseEntity<?> response = service.findLimByAccId(accountId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(limitList, response.getBody());
    }

    @Test
    public void findLimByAccId_ExpectNoLimitsSetMessageReturned() {

        // Arrange
        long accountId = 1L;
        when(repository.findByAccountId(accountId)).thenReturn(Collections.emptyList());
        when(accValidator.checkAccountId(accountId)).thenReturn(true);

        // Act
        ResponseEntity<?> response = service.findLimByAccId(accountId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No limits have been set yet.", response.getBody());
    }

    @Test
    void findTransWithFlagsByAccIdTest() {

        // Arrange
        long accountId = 1;
        when(repository.findTransactionsWithFlagsAndLimits(accountId))
                .thenReturn(Arrays.asList(new Object[]{1, "false"}, new Object[]{2, "true"}));

        // Act
        ResponseEntity<List<Object[]>> response = service.findTransWithFlagsByAccId(accountId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Object[]> transactionList = response.getBody();
        assertEquals(2, transactionList.size());
        assertEquals(1, transactionList.get(0)[0]);
        assertEquals("false", transactionList.get(0)[1]);
        assertEquals(2, transactionList.get(1)[0]);
        assertEquals("true", transactionList.get(1)[1]);
    }

    @Test
    void saveLimitInAccTest() {

        // Arrange
        long id = 1;
        LimitDto testData = new LimitDto(new BigDecimal("1000"), new BigDecimal("1000"),
                "USD", "product", 1);

        // Mock repository behaviour
        when(repository.findTopByAccIdAndCategoryOrdByIdDesc(eq(id), anyString())).thenReturn(Optional.of(testData));
        when(accValidator.checkAccountId(id)).thenReturn(true);
        when(entityValidator.validate(testData)).thenReturn(true);

        // Act
        ResponseEntity<String> response = service.saveLimitInAcc(testData, id);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("The limit has been successfully set.", response.getBody());
        verify(repository, times(1)).save(any(LimitDto.class));
    }

    /**ПРОВЕРКА ФЛАГОВ **/
    @Test
    void updateLimitRem_WhenNewLimitRemNegative_ThenFlagTrue() {
        TransactionDto trans = new TransactionDto("00001", "00003",
                "USD", new BigDecimal("500"), "product");
        LimitDto lim = new LimitDto(new BigDecimal("600"), new BigDecimal("400"),
                "USD", "product", 1);
        service.createLimSumIfEmptyAndUpdateLimitRem(lim, trans);
        assertEquals(lim.getLimitRem().doubleValue(), -100);
        assertEquals(trans.getFlagDto().getFlag(), "true");
    }

    @Test
    void updateLimitRem_WhenNewLimitRemPositive_ThenFlagFalse() {
        TransactionDto trans = new TransactionDto("00001", "00003",
                "USD", new BigDecimal("500"), "product");
        LimitDto lim = new LimitDto(new BigDecimal("1000"), new BigDecimal("600"),
                "USD", "product", 1);
        service.createLimSumIfEmptyAndUpdateLimitRem(lim, trans);
        assertEquals(lim.getLimitRem().doubleValue(), 100);
        assertEquals(trans.getFlagDto().getFlag(), "false");
    }


}