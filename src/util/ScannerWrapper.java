package util;

import exception.ScannerInputMismatchException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerWrapper {
    private final Scanner scanner;

    public ScannerWrapper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getStringInput() {
        try {
            String input = scanner.next();
            scanner.nextLine();
            return input;
        } catch (InputMismatchException e) {
            throw new ScannerInputMismatchException("Por favor digite um valor de texto correto");
        }
    }

    public Double getDoubleInput() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            throw new ScannerInputMismatchException("Por favor digite um valor numérico correto");
        }
    }

    public Integer getIntInput() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new ScannerInputMismatchException("Por favor digite uma opção válida");
        }
    }
}
