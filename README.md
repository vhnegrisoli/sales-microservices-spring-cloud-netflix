# Projeto - Sales Microservices Spring Cloud Netflix

Projeto de uma arquitetura de microsserviços utilizando Spring Boot e a stack Cloud Netflix (Zuul e Eureka), simulando um
pequeno sistema de vendas utilizando 3 APIs, uma para autenticação JWT, uma para persistir informações sobre produtos com PostgreSQL e uma para registrar as vendas utilizando um banco de dados MongoDB e uma aplicação reativa com Spring Webflux. A arquitetura se comunica assincronamente via RabbitMQ e sincronamente via chamada REST com Spring Cloud OpenFeign.

## Objetivos

O objetivo foi criar um projeto prático enquanto realizava o curso [Arquitetura Microserviços com Spring Cloud Netflix](https://www.udemy.com/course/spring-cloud-netflix/) na Udemy do professor Francis Klay Rocha.

## Tecnologias utilizadas

* **Java 11**
* **Spring Boot 2.3**
* **REST API**
* **Spring Cloud Netflix Eureka Service Discovery**
* **Spring Cloud Netflix Zuul**
* **Spring Webflux**
* **Spring Data JPA**
* **Reactive Spring Data MongoDB**
* **Spring Security**
* **MongoDB**
* **PostgreSQL**
* **Docker**
* **Docker-Compose**
* **JWT**
* **Swagger**
* **Gradle**
* **RabbitMQ**

## Arquitetura do projeto

![Arquitetura](https://github.com/vhnegrisoli/sales-microservices-spring-cloud-netflix/blob/master/imgs/Arquitetura%20-%20Spring%20Cloud%20Netflix.png)

## Executando a aplicação

É possível executar as aplicações separadamente, local em sua máquina, ou via Docker-compose.

#### Executando localmente

Para executar localmente, é necessário ter o Gradle, o Java 11 ou superior, uma instância do PostgreSQL, uma instância do MongoDB e uma instância do RabbitMQ em sua máquina.

Para executar as aplicações, basta executar na raiz de cada aplicação Java | Spring o comando:

`gradle build`

Após conseguir ter um build de sucesso, execute com:

`gradle bootRun`

#### Executando via Docker-compose

Caso você tenha o Docker em sua máquina, basta rodar o comando:

`docker-compose up --build`

Para esconder os logs no terminal, rode o comando com a flag `-d`.

#### Acesso

As aplicações ficarão nos seguintes endereços:

* Auth API -> 				http://localhost:8080
* Product API -> 			http://localhost:8081
* Sales API -> 				http://localhost:8082
* Service Discovery API -> 	http://localhost:8083
* Gateway API ->			http://localhost:3000

Outras aplicações:

* RabbitMQ -> 				http://localhost:5172
* RabbitMQ Dashboard ->		http://localhost:15172
* PostgreSQL ->				http://localhost:5432
* MongoDB ->				http://localhost:27017

## Documentação

A documentação de cada API é utilizando o Swagger. Os projetos que possuem documentação do Swagger são: Auth API, Products API e Sales API.

O endereço de acesso de cada documentação é:

* Auth API -> 				http://localhost:8080/swagger-ui.html
* Product API ->			http://localhost:8081/swagger-ui.html
* Sales API -> 				http://localhost:8082/swagger-ui.html

## Autor

#### Victor Hugo Negrisoli
#### Desenvolvedor de Software Back-End
