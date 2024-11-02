package com.exemplo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class JsonPlaceholderTest {

    @Test
    public void testGetPosts() {
        // Definindo a base URI da API
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";

        // Realizando a requisição GET e armazenando a resposta
        Response response = given()
            .when()
                .get("/posts")
            .then()
                .statusCode(200) // Verificando se o código de status é 200
                .extract()
                .response();

        // Verificando o corpo da resposta (opcional)
        System.out.println("Corpo da Resposta: " + response.asString());

        // Exemplo de verificação de dados no corpo da resposta
        // Aqui, estamos verificando se o primeiro post tem userId 1
        given()
            .when()
                .get("/posts")
            .then()
                .body("[0].userId", equalTo(1)); // Verificação do primeiro post
    }

    @Test
    public void testCreatePost() {
        // Definindo a base URI da API
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";

        // Criando uma nova postagem
        String requestBody = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }";

        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/posts")
        .then()
            .statusCode(201) // Verificando se o código de status é 201
            .body("title", equalTo("foo")) // Verificando o título
            .body("body", equalTo("bar")) // Verificando o corpo
            .body("userId", equalTo(1)); // Verificando o userId
    }
}
