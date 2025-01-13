package com.example.labtestproject.entity;

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
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bill;

    public AccountEntity(String bill) {
        this.bill = bill;
    }
}
