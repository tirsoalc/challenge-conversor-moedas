package ui;

import exception.ScannerInputMismatchException;
import service.ExchangeRateService;
import util.ScannerWrapper;

import java.util.Arrays;
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
        String[] operations = operationMenu();
        int exitOption = searchForExitOption(operations);
        int lastMenuOption = operations.length;
        int firstMenuOption = 1;
        while(true) {
            showOperationMenu(operations);
            try {
                int option = scannerWrapper.getIntInput();
                if (option == exitOption) break;
                if (option < firstMenuOption || option > lastMenuOption) continue;

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

    private String[] operationMenu() {
        return new String[] {
                "1) Converter moeda",
                "2) Sair"
        };
    }

    private void showOperationMenu(String[] options) {
        for(String option : options) {
            System.out.println(option);
        }
    }

    private int searchForExitOption(String[] options) {
        for(String option : options) {
            if (option.contains("Sair")) return Character.getNumericValue(option.charAt(0));
        }
        throw new RuntimeException("[Error]: Menu sem opção de saída correta, Exemplo de opção saída correta: \"4) Sair\" com S maiúsculo");
    }
}
