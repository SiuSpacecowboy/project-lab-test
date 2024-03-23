package com.example.labtestproject.services;

import com.example.labtestproject.subClasses.TwelveDataRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/** Класс запроса к внешнему API для получения актуального курса валют. */
@Service
public class WebClientService {

    private static final String ZONE = "Europe/Moscow";
    private static final String currencyRUB = "USD/RUB";
    private static final String currencyKZT = "USD/KZT";
    private static final String interval = "1day";
    private static final String key = "4cb2b0592cb646a6851e1451f4ffc378";

    private final WebClient webClient;

    @Autowired
    public WebClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    /** Метод, использующий асинхронную среду для запроса к внешнему api. */
    public List<TwelveDataRequestResult> getTwelveDataRequestResult() {
        List<String> currencies = List.of(currencyRUB, currencyKZT);
        return Flux.fromIterable(currencies)
                .map(v -> {
                    try {
                        return getUri(v, LocalDate.now().minusDays(7), LocalDate.now());
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(this::requestResultMono)
                .collectList()
                .block();
    }

    public URI getUri(String currency, LocalDate startDate, LocalDate endDate) throws URISyntaxException {
        return new URI("https://api.twelvedata.com"
                + "/time_series?symbol=" + currency
                + "&timezone=" + ZONE
                + "&start_date=" + startDate
                + "&end_date=" + endDate
                + "&interval=" + interval
                + "&apikey=" + key);

    }

    /** Получение курса для одной валюты. */
    public Mono<TwelveDataRequestResult> requestResultMono(URI uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(TwelveDataRequestResult.class);
    }

}
