package api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiKeyLoader;
import db.CurrencyNameDatabase;
import exception.ExchangeRateApiException;
import http.HttpService;
import model.*;

import java.util.Optional;

public class ExchangeRateApiClientImpl implements ExchangeRateApiClient {

    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private final String PAIR_CONVERSION_ENDPOINT = "/pair/";
    private final String API_KEY = ApiKeyLoader.getApiKey();
    private final FieldNamingPolicy UNDERSCORE_NAMING_POLICY = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
    private final FieldNamingPolicy DASHES_NAMING_POLICY = FieldNamingPolicy.LOWER_CASE_WITH_DASHES;

    private HttpService client;
    private CurrencyNameDatabase currencyNameDatabase;


    public ExchangeRateApiClientImpl(HttpService client, CurrencyNameDatabase currencyNameDatabase) {
        this.client = client;
        this.currencyNameDatabase = currencyNameDatabase;
    }


    @Override
    public Optional<CurrencyPair> getExchangeRatePair(String baseCurrencyAcronym, String targetCurrencyAcronym, Double amount) {
        String url = makeExchangeRatePairUrl(baseCurrencyAcronym, targetCurrencyAcronym, amount);

        String infoLog = String.format("[Info]: Chamando a ExchangeRate API; " +
                "contexto={endpoint: %s, moeda origem: %s, moeda final: %s, quantidade: %.2f}",
                PAIR_CONVERSION_ENDPOINT,baseCurrencyAcronym,targetCurrencyAcronym,amount);
        System.out.println(infoLog);
        Optional<String> bodyResponse = client.get(url);

        if (bodyResponse.isEmpty()) {
            return Optional.empty();
        }

        String jsonResponse = bodyResponse.get();
        checkForResponseError(jsonResponse);

        ExchangeRatePairResponse exchangeRatePairResponse = toExchangeRatePairResponse(jsonResponse);

        String baseCurrencyName = currencyNameDatabase.getPortugueseName(baseCurrencyAcronym);
        String targetCurrencyName = currencyNameDatabase.getPortugueseName(targetCurrencyAcronym);

        CurrencyPair currencyPair = exchangeRatePairResponse.toCurrencyPair(baseCurrencyName, targetCurrencyName, amount);

        return Optional.of(currencyPair);
    }

    private String makeExchangeRatePairUrl(String baseCurrencyAcronym, String targetCurrencyAcronym, Double amount) {
        String fromToAmount = baseCurrencyAcronym+"/"+targetCurrencyAcronym+"/"+amount;
        String url = BASE_URL + API_KEY + PAIR_CONVERSION_ENDPOINT + fromToAmount;
        return url;
    }

    private void checkForResponseError(String jsonResponse) {
        if(jsonResponse.contains("error")) {
            ExchangeRateErrorResponse exchangeRateErrorResponse = toExchangeRateErrorResponse(jsonResponse);
            System.out.println(exchangeRateErrorResponse);
            System.out.println("[Error]: ExchangeRate Api Error " + exchangeRateErrorResponse.errorType());
            throw new ExchangeRateApiException("ExchangeRate API Error: " + exchangeRateErrorResponse.errorType());
        }
    }

    private ExchangeRatePairResponse toExchangeRatePairResponse(String json) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(UNDERSCORE_NAMING_POLICY)
                .create();
        return gson.fromJson(json, ExchangeRatePairResponse.class);
    }

    private ExchangeRateErrorResponse toExchangeRateErrorResponse(String json) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(DASHES_NAMING_POLICY)
                .create();
        return gson.fromJson(json, ExchangeRateErrorResponse.class);
    }

}
