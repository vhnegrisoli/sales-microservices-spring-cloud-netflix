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

* Auth API ->               http://localhost:8080/auth
* Product API ->            http://localhost:8081/product
* Sales API ->              http://localhost:8082/sales
* Service Discovery API ->  http://localhost:8083
* Gateway API ->            http://localhost:3000/api

Outras aplicações:

* RabbitMQ ->               http://localhost:5172
* RabbitMQ Dashboard ->     http://localhost:15172
* PostgreSQL ->             http://localhost:5432
* MongoDB ->                http://localhost:27017

## Documentação

A documentação de cada API é utilizando o Swagger. Os projetos que possuem documentação do Swagger são: Auth API, Products API e Sales API.

O endereço de acesso de cada documentação é:

* Auth API ->               http://localhost:8080/auth/swagger-ui.html
* Product API ->            http://localhost:8081/product/swagger-ui.html
* Sales API ->              http://localhost:8082/sales/swagger-ui.html

## API Gateway com Zuul

O projeto API Gateway utiliza a tecnologia `Spring Cloud Netflix Zuul Proxy` que permite criar um gateway entre as 3 APIs: Sales, Auth e Product.

O path padrão desse projeto é `/api`.

Para acessar os projetos acima apenas via Gateway:

* Auth:                     http://localhost:3000/api/auth
* Product:                  http://localhost:3000/api/product
* Sales:                    http://localhost:3000/api/sales

É possível acessar o Swagger pelo Gateway também ao invés de acessar cada API individualmente, basta acessar os caminhos acima, porém, com `swagger-ui.html` ao fim.

## Obter Token de Acesso

```
Request:

Método: POST
URL: http://localhost:8080/auth/token (http://localhost:3000/api/auth/token via Gateway)
Body: 
{
    "username": "test_user",
    "password": "123456"
}

Response:

Status: 200 | OK

{
    "username": "test_user",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}

```

Exemplo de uso do Access Token em algum endpoint:

```
Request:

Método: GET
URL: http://localhost:8081/product (http://localhost:3000/api/product via Gateway)
Headers: 
{
    "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}

Response:

Status: 200 | OK

{
 Add Response    
}

Caso não envie o Header de Authorization em qualquer endpoint protegido, o retorno será:

{
    Add Response
}

```

## Autor

#### Victor Hugo Negrisoli
#### Desenvolvedor de Software Back-End
