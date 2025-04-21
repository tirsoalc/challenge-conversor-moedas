package ui;

import exception.ScannerInputMismatchException;
import service.ExchangeRateService;
import util.ScannerWrapper;

import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;
    private ExchangeRateService service;
    private ScannerWrapper scannerWrapper;

    public ConsoleUI(ExchangeRateService service) {
        this.scanner = new Scanner(System.in);
        this.scannerWrapper = new ScannerWrapper(this.scanner);

        this.service = service;
    }

    public void mountUI() {
        System.out.println("Seja bem-vindo/a ao Conversor de Moeda");
        while(true) {
            operationMenu();

            try {
                int option = scannerWrapper.getIntInput();
                if (option == 2) break;

                System.out.println("Digite a sigla moeda de origem ex: USD, BRL, AUS");
                String baseCurrency = scannerWrapper.getStringInput();
                System.out.println("Digite a sigla da moeda para qual a moeda de origem será convertida ex: USD, BRL, AUS");
                String targetCurrency = scannerWrapper.getStringInput();
                System.out.println("Digite a quantidade da moeda que será convertida");
                Double amount = scannerWrapper.getDoubleInput();

                String result = service.getExchangeRatePair(baseCurrency, targetCurrency, amount);
                System.out.println(result);
            } catch (ScannerInputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.next();
            }
        }
        System.out.println("[Info]: Fechando o scanner");
        scanner.close();
    }

    public void operationMenu() {
        System.out.println("1) Converter moeda");
        System.out.println("2) Sair");
    }
}
