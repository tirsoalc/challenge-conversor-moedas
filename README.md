# CHALLENGE ONE - **Conversor de Moedas**

Este projeto é um **conversor de moedas** desenvolvido em Java como parte desafio Challenge ONE proposto pela parceria **Alura + Oracle Next Education**. A aplicação permite a conversão de valores entre diferentes moedas de forma simples e interativa via console, utilizando uma API externa para obter taxas de câmbio em tempo real.

## Visão Geral

O objetivo desse projeto é criar uma aplicação que permita a interação do usuário via console para conversão de valores entre diferentes moedas, utilizando-se de uma API externa para obter as taxas de câmbio atualizadas.

## Tecnologias Utilizadas

- **Java 17**
- **API externa de taxas de câmbio** (no projeto foi utilizado a ExchangeRate API)
- **Biblioteca GSON**

## Como Executar o Projeto

1. Clone o repositório ou faça o download clicando botão Download ZIP (Ambos são encontrados ao clicar em "**<> Code**").

2. Navegue até o diretório do projeto

3. No diretório raiz, há um arquivo nomeado como `config.properties.example`, renomeie ou copie o conteúdo do arquivo para outro chamado `config.properties`

4. Insira a sua chave de API no arquivo `config.properties`:

```
apiKey=SUA_CHAVE_API
```
5. Baixe a biblioteca externa **Gson** (versão 2.11.0) em: [Download via Maven Repository](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.11.0)

5. Inclua o `.jar` **Gson** ao classpath do projeto. 

6. Compile os arquivos .java presentes dentro da pasta `src`.

7. Execute a aplicação iniciando pela classe `Main`.

## Diferenças em relação ao projeto proposto

O projeto original proposto sugere uma lista "padrão" para a conversão de algumas moedas. No entanto, optei por seguir um caminho diferente e deixar que o usuário escolha quais moedas serão utilizadas na conversão, desta maneira, o projeto segue uma abordagem mais flexível e adaptável. 
