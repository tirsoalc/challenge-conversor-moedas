package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiKeyLoader {
    private static Properties properties = new Properties();

    static {
        try(FileInputStream input = new FileInputStream("config.properties")){
            properties.load(input);
            if(!properties.containsKey("api.key")) {
                System.out.println("[Error]: A api.key não foi encontrada no arquivo config.properties");
                throw new RuntimeException("A api.key não foi encontrada no arquivo config.properties");
            }
            System.out.println("[Info]: A api.key foi encontrada");

        } catch (IOException e) {
            System.out.println("[Error]: O arquivo config.properties não foi encontrado na raiz do projeto");
            throw new RuntimeException(e);
        }
    }

    public static String getApiKey() {
        return properties.getProperty("api.key");
    }
}
