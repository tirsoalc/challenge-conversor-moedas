package model;

public record ExchangeRatePairResponse(String baseCode, String targetCode, Double conversionResult) {
    public CurrencyPair toCurrencyPair(String baseCurrencyName, String targetCurrencyName, Double baseAmount) {
        return new CurrencyPair(baseCurrencyName,targetCurrencyName,baseAmount,conversionResult);
    }
}
