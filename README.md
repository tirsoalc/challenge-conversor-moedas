# CHALLENGE ONE - **Conversor de Moedas**

Este projeto é um **conversor de moedas** desenvolvido em Java como parte desafio Challenge ONE proposto pela **Alura + Oracle Next Education** que utiliza uma API externa para obter taxas de câmbio em tempo real. A aplicação permite converter valores entre diferentes moedas de forma simples e interativa via console.

## Visão Geral

O objetivo desse projeto é criar uma aplicação que permita a interação do usuário via console para conversão de valores entre diferentes moedas, utilizando-se de uma API externa para obter as taxas de câmbio atualizadas.

## Tecnologias Utilizadas

- **Java 17**
- **API externa de taxas de câmbio** (no projeto foi utilizado a ExchangeRate API)
- **Biblioteca GSON**

## Como Executar o Projeto

1. Para obter o código fonte basta apenas clonar o repositório ou fazer o download no botão Download ZIP (Ambos são encontrados ao clicar em "<> Code").

2. Navegue até o diretório do projeto

3. No diretório raiz há um arquivo nomeado como "config.properties.example", renomeie ou copie o conteúdo do arquivo para outro chamado "**config.properties**"

```
apiKey=SUA_CHAVE_API
```

4. Inclua a biblioteca externa Gson (versão 2.11.0) ao classpath. 

```
Faça o download manual do .jar em: https://mvnrepository.com/artifact/com.google.code.gson/gson/2.11.0
```

5. Compile os arquivos .java presentes dentro da pasta `src`.

6. Execute a aplicação iniciando pela classe `Main`.

