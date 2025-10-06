package com.banco.abc.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;

import com.banco.abc.exchangerate.exception.ExchangeRateException;
import com.banco.abc.exchangerate.model.ExchangeRateResponse;
import com.banco.abc.exchangerate.service.ExchangeRateService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * ExchangeRateControllerTest
 * Integration tests for the exchange rate controller endpoints.
 * Tests various scenarios including successful queries, validation errors, and business logic failures.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */
@QuarkusTest
class ExchangeRateControllerTest {

  private static final String VALID_DNI = "12345678";
  private static final String INVALID_DNI_SHORT = "123";
  private static final String INVALID_DNI_LETTERS = "1234567a";
  private static final String ENDPOINT = "/api/v1/exchange-rate";
  @InjectMock
  ExchangeRateService exchangeRateService;

  @Test
  void testGetExchangeRateSuccess() {
    // Given
    var mockResponse = new ExchangeRateResponse(
        LocalDate.now(),
        new BigDecimal("3.546"),
        new BigDecimal("3.540"),
        new BigDecimal("3.552")
    );

    Mockito.when(exchangeRateService.getExchangeRate(VALID_DNI))
        .thenReturn(mockResponse);

    // When & Then
    given()
        .queryParam("dni", VALID_DNI)
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("fecha", notNullValue())
        .body("sunat", equalTo(3.546f))
        .body("compra", equalTo(3.540f))
        .body("venta", equalTo(3.552f));
  }

  @Test
  void testGetExchangeRateWithMissingDni() {
    given()
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("parameterViolations[0].message", containsString("DNI es requerido"));
  }

  @Test
  void testGetExchangeRateWithInvalidDniShort() {
    given()
        .queryParam("dni", INVALID_DNI_SHORT)
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("parameterViolations[0].message",
            containsString("DNI debe contener exactamente 8 digitos"));
  }

  @Test
  void testGetExchangeRateWithInvalidDniLetters() {
    given()
        .queryParam("dni", INVALID_DNI_LETTERS)
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("parameterViolations[0].message",
            containsString("DNI debe contener exactamente 8 digitos"));
  }

  @Test
  void testGetExchangeRateWithDailyLimitExceeded() {
    // Given
    Mockito.when(exchangeRateService.getExchangeRate(VALID_DNI))
        .thenThrow(ExchangeRateException.limitExceeded(10));

    // When & Then
    given()
        .queryParam("dni", VALID_DNI)
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(429)
        .contentType(ContentType.JSON)
        .body("error", containsString("Has excedido el limite de 10 consultas diarias"));
  }

  @Test
  void testGetExchangeRateWithExternalApiError() {
    // Given
    Mockito.when(exchangeRateService.getExchangeRate(VALID_DNI))
        .thenThrow(ExchangeRateException.externalApiError("Connection timeout"));

    // When & Then
    given()
        .queryParam("dni", VALID_DNI)
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(503)
        .contentType(ContentType.JSON)
        .body("error", containsString("Error consultando tipo de cambio"));
  }

  @Test
  void testGetExchangeRateWithInternalServerError() {
    // Given
    Mockito.when(exchangeRateService.getExchangeRate(anyString()))
        .thenThrow(new RuntimeException("Unexpected error"));

    // When & Then
    given()
        .queryParam("dni", VALID_DNI)
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(500)
        .contentType(ContentType.JSON)
        .body("error", equalTo("Error interno del servidor"))
        .body("timestamp", notNullValue());
  }

  @Test
  void testGetExchangeRateWithEmptyDni() {
    given()
        .queryParam("dni", "")
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("parameterViolations[0].message",
            anyOf(containsString("DNI es requerido"),
                containsString("DNI debe contener exactamente 8 digitos")));
  }

  @Test
  void testGetExchangeRateWithDniTooLong() {
    given()
        .queryParam("dni", "123456789")
        .when()
        .get(ENDPOINT)
        .then()
        .statusCode(400)
        .contentType(ContentType.JSON)
        .body("parameterViolations[0].message",
            containsString("DNI debe contener exactamente 8 digitos"));
  }

  @Test
  void testOpenApiDocumentationAvailable() {
    given()
        .when()
        .get("/openapi")
        .then()
        .statusCode(200)
        .contentType("application/yaml;charset=UTF-8")
        .body(containsString("Exchange Rate Service API"))
        .body(containsString("1.0.0"));
  }

  @Test
  void testSwaggerUiAvailable() {
    given()
        .when()
        .get("/swagger-ui")
        .then()
        .statusCode(200);
  }
}
