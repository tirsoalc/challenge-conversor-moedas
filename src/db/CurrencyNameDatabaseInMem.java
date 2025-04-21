package db;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiKeyLoader;
import http.HttpService;
import http.HttpServiceImpl;
import model.ExchangeRateErrorResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CurrencyNameDatabaseInMem implements CurrencyNameDatabase{

    private static Map<String, String> portugueseTranslationMap = new HashMap<>();
    private static Map<String, String> englishTranslationMap = new HashMap<>();
    private static final String API_KEY = ApiKeyLoader.getApiKey();

    static {
        populatePortugueseTranslationMap();
        populateEnglishTranslationMap();
    }

    private static void populatePortugueseTranslationMap() {
        portugueseTranslationMap.put("ARS", "Peso Argentino");
        portugueseTranslationMap.put("AUD", "Dólar Australiano");
        portugueseTranslationMap.put("BRL", "Real Brasileiro");
        portugueseTranslationMap.put("CAD", "Dólar Canadense");
        portugueseTranslationMap.put("EUR", "Euro");
        portugueseTranslationMap.put("USD", "Dólar Americano");
    }

    private static void populateEnglishTranslationMap() {
        //Apenas simulando um banco de dados pré-existente utilizando-se da API externa para popular o nome das moedas
        HttpService httpService = new HttpServiceImpl();
        Optional<String> jsonResponse = Optional.ofNullable(
                httpService.get("https://v6.exchangerate-api.com/v6/" + API_KEY + "/codes")
                        .get().body());

        if (jsonResponse.isPresent() && jsonResponse.get().contains("error")) {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
            ExchangeRateErrorResponse errorResponse = gson.fromJson(jsonResponse.get(),ExchangeRateErrorResponse.class);
            System.out.println("[Error]: Erro ao tentar se comunicar com a API; contexto={endpoint: /codes, erro: "+errorResponse.errorType()+"}");
            throw new RuntimeException("Erro inesperado: " + errorResponse.errorType());
        }

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        ApiResponse apiResponse = gson.fromJson(jsonResponse.get(), ApiResponse.class);
        for (List<String> supportedCode : apiResponse.supportedCodes) {
            String acronym = supportedCode.get(0);
            String englishName = supportedCode.get(1);
            englishTranslationMap.put(acronym, englishName);
        }


    }

    private static class ApiResponse {
        public List<List<String>> supportedCodes;
    }

    @Override
    public String getPortugueseName(String acronym) {
        String acronymUpperCase = acronym.toUpperCase();
        if (!portugueseTranslationMap.containsKey(acronymUpperCase)) {
            String englishTranslation = englishTranslationMap.get(acronymUpperCase);
            System.out.println("[Info]: Tradução da sigla não disponível; contexto={"+acronymUpperCase+"-"+englishTranslation+"}");
            return englishTranslation;
        }
        String portugueseTranslation = portugueseTranslationMap.get(acronymUpperCase);
        System.out.println("[Info]: Tradução disponível; contexto={"+acronymUpperCase+"-"+portugueseTranslation+"}");

        return portugueseTranslation;
    }
}
