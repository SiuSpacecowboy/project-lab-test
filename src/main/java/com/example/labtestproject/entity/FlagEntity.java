package com.example.labtestproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO для таблицы flags. */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "flags")
public class FlagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String flag;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trans_id", referencedColumnName = "id")
    private TransactionEntity transactionEntity;

    public FlagEntity(String flag, TransactionEntity transactionEntity) {
        this.flag = flag;
        this.transactionEntity = transactionEntity;
    }
}
