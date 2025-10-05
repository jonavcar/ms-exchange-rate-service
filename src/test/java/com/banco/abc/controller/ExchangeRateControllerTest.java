package com.banco.abc.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * =============================================================
 * Class: ExchangeRateControllerTest
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Tests de integración para el controlador de tipo de cambio
 * =============================================================
 */
@QuarkusTest
public class ExchangeRateControllerTest {

    @Test
    public void testExchangeRateEndpointWithValidDni() {
        given()
                .when().get("/api/v1/exchange-rate?dni=12345678")
                .then()
                .statusCode(200)
                .body("fecha", notNullValue())
                .body("sunat", notNullValue())
                .body("compra", notNullValue())
                .body("venta", notNullValue());
    }

    @Test
    public void testExchangeRateEndpointWithInvalidDni() {
        given()
                .when().get("/api/v1/exchange-rate?dni=123")
                .then()
                .statusCode(500);
    }

    @Test
    public void testExchangeRateEndpointWithoutDni() {
        given()
                .when().get("/api/v1/exchange-rate")
                .then()
                .statusCode(500);
    }
}
