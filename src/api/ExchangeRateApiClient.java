package api;

import model.CurrencyPair;

import java.util.Optional;

public interface ExchangeRateApiClient {
    Optional<CurrencyPair> getExchangeRatePair(String fromCurrencyAcronym, String toCurrencyAcronym, Double amount);
}
