package com.example.labtestproject.dto;

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
public class FlagDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String flag;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trans_id", referencedColumnName = "id")
    private TransactionDto transactionDto;

    public FlagDto(String flag, TransactionDto transactionDto) {
        this.flag = flag;
        this.transactionDto = transactionDto;
    }
}
