import api.ExchangeRateApiClient;
import api.ExchangeRateApiClientImpl;
import db.CurrencyNameDatabase;
import db.CurrencyNameDatabaseInMem;
import http.HttpService;
import http.HttpServiceImpl;
import service.ExchangeRateService;
import service.ExchangeRateServiceImpl;
import ui.ConsoleUI;


public class Main {
    public static void main(String[] args) {

        System.out.println("[Info] : Programa sendo iniciado");

        HttpService client = new HttpServiceImpl();
        CurrencyNameDatabase currencyNameDatabase = new CurrencyNameDatabaseInMem();
        ExchangeRateApiClient apiClient = new ExchangeRateApiClientImpl(client, currencyNameDatabase);
        ExchangeRateService service = new ExchangeRateServiceImpl(apiClient);
        ConsoleUI consoleUI = new ConsoleUI(service);
        consoleUI.mountUI();

        System.out.println("[Info] : Programa sendo finalizado");
    }
}