package com.example.labtestproject.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO для таблицы accounts. */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class AccountDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bill;

    public AccountDto(String bill) {
        this.bill = bill;
    }
}
