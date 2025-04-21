package service;
public interface ExchangeRateService {
    String getExchangeRatePair(String fromCurrencyAcronym, String toCurrencyAcronym, Double amount);
}
