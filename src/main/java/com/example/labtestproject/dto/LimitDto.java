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

/** DTO для таблицы limits. */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "limits")
public class LimitDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Field 'limit' must be not null")
    @DecimalMin(value = "0.0", message = "Field 'limit' mustn't be less than zero")
    @Digits(integer = 8, fraction = 5, message = "Too many numbers for field 'limit'. Enter a smaller quantity.")
    private BigDecimal limitSum;
    private BigDecimal limitRem;

    @NotEmpty(message = "Field 'expenseCategory' must be not empty.")
    @Pattern(regexp = "product|service", message = "Field 'expenseCategory' must be either \"product\" or \"service\".")
    private String expenseCategory;

    @CreationTimestamp
    private LocalDateTime limitDatetime;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "limit_currency_shortname")
    private String limitCurrencyShortname;

    public LimitDto(BigDecimal limitSum, BigDecimal limitRem, String limitCurrencyShortname,
                    String expenseCategory, long accountId) {
        this.limitSum = limitSum;
        this.limitRem = limitRem;
        this.limitCurrencyShortname = limitCurrencyShortname;
        this.expenseCategory = expenseCategory;
        this.accountId = accountId;
    }
}
