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
                System.out.println("[Error]: O campo api.key não foi encontrado no arquivo config.properties");
                throw new RuntimeException("O campo api.key não foi encontrado no arquivo config.properties");
            }
            System.out.println("[Info]: O campo api.key foi encontrado no arquivo config.properties");

        } catch (IOException e) {
            System.out.println("[Error]: O arquivo config.properties não foi encontrado na raiz do projeto");
            throw new RuntimeException(e);
        }
    }

    public static String getApiKey() {
        return properties.getProperty("api.key");
    }
}
