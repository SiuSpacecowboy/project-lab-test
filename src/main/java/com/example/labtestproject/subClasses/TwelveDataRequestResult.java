package com.example.labtestproject.subClasses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/** Побочный класс для преобразования JSON-ответа. */
@Getter
@Setter
@ToString
public class TwelveDataRequestResult {

    private Meta meta;
    private List<Value> values;
    private String status;

    @Getter
    @Setter
    @ToString
    public static class Meta {
        private String symbol;
        private String interval;
        private String currency_base;
        private String currency_quote;
        private String type;
    }

    @Getter
    @Setter
    @ToString
    public static class Value {
        private String datetime;
        private String open;
        private String high;
        private String low;
        private String close;
    }
}