# API Planetas Star Wars

Este repositório foi criado com fins acadêmicos para estudo e prática de **testes automatizados** em aplicações desenvolvidas com **Spring Boot**.  
A aplicação é um serviço web que provê dados sobre a franquia de Star Wars, mais especificamente sobre os planetas que aparecem nos filmes.

Esse projeto foi elaborado durante o curso [Testes automatizados na prática com Spring Boot](https://www.udemy.com/course/testes-automatizados-na-pratica-com-spring-boot/?referralCode=7F6C5AA14AE558497FE0), em que o foco foi a criação de testes automatizados.
## Objetivos do Projeto

- Praticar **boas práticas de testes automatizados** em aplicações Java com Spring;
- Explorar diferentes bibliotecas e frameworks de teste;
- Aplicar ferramentas de **testes unitários**, **integração** e **mutação**;
- Utilizar métricas de cobertura de testes para avaliar a qualidade do código.

## Tecnologias e Ferramentas Utilizadas

- **Spring Boot** – Framework para criação da API;
- **JUnit 5** – Framework principal de testes;
- **Mockito** – Mocking para isolamento de dependências;
- **AssertJ** – Assertivas mais legíveis e expressivas;
- **Hamcrest** – Matchers para validações avançadas;
- **JsonPath** – Validação de respostas JSON em testes;
- **JaCoCo** – Relatórios de cobertura de código;
- **PITest** – Testes de mutação para avaliação da robustez dos testes.

## Construir e Executar

Para construir e testar, execute o comando:

```sh
$ ./mvnw clean verify
```