package com.example.labtestproject.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** DTO для таблицы transactions. */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "transactions")
public class TransactionDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Field 'accountFrom' must be not empty.")
    @Pattern(regexp = "\\d{10}", message = "Field 'accountFrom' must contain 10 digits and no another symbols.")
    private String accountFrom;

    @NotEmpty(message = "Field 'accountTo' must be not empty.")
    @Pattern(regexp = "\\d{10}", message = "Field 'accountTo' must contain 10 digits and no another symbols.")
    private String accountTo;

    @NotEmpty(message = "Field 'currencyShortName' must be not empty.")
    @Pattern(regexp = "(?i)rub|kzt|usd", message = "Field 'expenseCategory' must be either \"usd\" or \"rub\" or \"kzt\".")
    @Column(name = "currency_shortname")
    private String currencyShortName;

    @NotNull(message = "Field 'sum' must be not null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Field 'sum' must be bigger than 0.")
    @Digits(integer = 8, fraction = 5, message = "Too many numbers for field 'sum'. Enter a smaller quantity.")
    private BigDecimal sum;

    @NotEmpty(message = "Field 'expenseCategory' must be not empty.")
    @Pattern(regexp = "product|service", message = "Field 'expenseCategory' must be either \"product\" or \"service\".")
    private String expenseCategory;


    @CreationTimestamp
    private LocalDateTime datetime;

    @Column(name = "account_id")
    private long accountId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transactionDto", orphanRemoval = true)
    private FlagDto flagDto;

    public TransactionDto(String accountFrom, String accountTo, String currencyShortName, BigDecimal sum,
                          String expenseCategory) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyShortName = currencyShortName;
        this.sum = sum;
        this.expenseCategory = expenseCategory;
    }
}
