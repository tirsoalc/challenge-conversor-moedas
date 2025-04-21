package service;

import api.ExchangeRateApiClient;
import exception.ExchangeRateApiException;
import model.CurrencyPair;

import java.util.Optional;

public class ExchangeRateServiceImpl implements ExchangeRateService{

    private ExchangeRateApiClient apiClient;

    public ExchangeRateServiceImpl(ExchangeRateApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public String getExchangeRatePair(String fromCurrencyAcronym, String toCurrencyAcronym, Double amount) {
        try {
            Optional<CurrencyPair> currencyPairOptional = apiClient.getExchangeRatePair(fromCurrencyAcronym, toCurrencyAcronym, amount);
            if (currencyPairOptional.isEmpty()) {
                System.out.println("[Warn] : O corpo da requisição está vazio");
                return "Algum erro ocorreu ao tentar converter a moeda, por favor tente novamente";
            }

            CurrencyPair currencyPair = currencyPairOptional.get();

            String baseCurrencyStr = "["+fromCurrencyAcronym+"/"+currencyPair.getBaseCurrencyName()+"]";
            String targetCurrencyStr = "["+toCurrencyAcronym+"/"+currencyPair.getTargetCurrencyName()+"]";
            String result = "Valor " + amount + baseCurrencyStr + "corresponde ao valor final de =>>> " + currencyPair.getConversionAmountResult() + targetCurrencyStr;

            return result;
        } catch (ExchangeRateApiException e) {
            return "Erro ao chamar a API. Consulte o log para informações adicionais";
        }
    }
}
