package api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiKeyLoader;
import db.CurrencyNameDatabase;
import exception.ExchangeRateApiException;
import http.HttpService;
import model.*;

import java.net.http.HttpResponse;
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
        Optional<HttpResponse<String>> response = client.get(url);

        if (response.isEmpty()) {
            return Optional.empty();
        }

        checkForResponseError(response.get());
        String jsonResponse = response.get().body();

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

    private void checkForResponseError(HttpResponse<String> response) {
        String bodyResponse = response.body();


        if (bodyResponse.contains("error")){
            ExchangeRateErrorResponse exchangeRateErrorResponse = toExchangeRateErrorResponse(bodyResponse);
            System.out.println("[Error]: ExchangeRate Api Error " + exchangeRateErrorResponse.errorType());
            throw new ExchangeRateApiException("ExchangeRate API Error: " + exchangeRateErrorResponse.errorType());
        }

        if (response.statusCode() == 404) {
            System.out.println("[Error]: Requisição não foi bem-sucedida; contexto={404 Not Found}");
            throw new ExchangeRateApiException("ExchangeRate API Error: 404 endereço não encontrado");
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
