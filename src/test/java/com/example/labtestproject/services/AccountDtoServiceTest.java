package com.example.labtestproject.services;

import com.example.labtestproject.dto.AccountDto;
import com.example.labtestproject.repositories.AccountDtoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountDtoServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountDtoRepository repository;

    @Mock
    AccountDtoRepository rep;

    @InjectMocks
    AccountDtoService service;

    @Test
    public void GetAccountsTest() throws Exception {
        AccountDto acc1 = new AccountDto("000001");
        acc1.setId(1);
        AccountDto acc2 = new AccountDto("000002");
        acc2.setId(2);
        List<AccountDto> accountList = List.of(acc1, acc2);

        when(repository.findAll()).thenReturn(accountList);

        mockMvc.perform(get("/api/v1/acc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].bill").value("000001"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].bill").value("000002"));
    }

    @Test
    public void testFindAccountByIdForController() {

        // Arrange
        long accountId = 1L;
        AccountDto accountDto = new AccountDto("000001");
        accountDto.setId(accountId);
        Mockito.when(rep.findById(accountId)).thenAnswer(invocation -> Optional.of(accountDto));

        // Act
        ResponseEntity<?> response = service.findAccountByIdForController(accountId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }

    @Test
    public void testFindAccountByIdForControllerAccountNotFound() {

        // Arrange
        long accountId = 100;
        when(rep.findById(accountId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = service.findAccountByIdForController(accountId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account with id = 100 doesn't exist", response.getBody());
    }

    @Test
    public void saveNewBillTest() {

        // Arrange
        when(rep.findMaxId()).thenReturn(Optional.of(1));

        // Act
        ResponseEntity<String> response = service.saveNewBill();

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New account has been created", response.getBody());
    }

}