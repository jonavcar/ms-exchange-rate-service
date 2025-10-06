package com.banco.abc.exchangerate.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * GlobalExceptionHandlerTest
 * Unit tests for global exception handler to verify error response formatting.
 * Tests exception mapping and consistent error response structure.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */
class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void testToResponseWithRuntimeException() {
    // Given
    var exception = new RuntimeException("Test runtime exception");

    // When
    Response response = exceptionHandler.toResponse(exception);

    // Then
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    assertNotNull(response.getEntity());

    String responseBody = response.getEntity().toString();
    assertTrue(responseBody.contains("Error interno del servidor"));
    assertTrue(responseBody.contains("timestamp"));
  }

  @Test
  void testToResponseWithNullPointerException() {
    // Given
    var exception = new NullPointerException("Null pointer test");

    // When
    Response response = exceptionHandler.toResponse(exception);

    // Then
    assertEquals(500, response.getStatus());
    assertNotNull(response.getEntity());

    String responseBody = response.getEntity().toString();
    assertTrue(responseBody.contains("Error interno del servidor"));
  }

  @Test
  void testToResponseWithIllegalArgumentException() {
    // Given
    var exception = new IllegalArgumentException("Invalid argument");

    // When
    Response response = exceptionHandler.toResponse(exception);

    // Then
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    assertNotNull(response.getEntity());
  }

  @Test
  void testToResponseWithGenericException() {
    // Given
    var exception = new Exception("Generic exception message");

    // When
    Response response = exceptionHandler.toResponse(exception);

    // Then
    assertEquals(500, response.getStatus());
    String responseBody = response.getEntity().toString();
    assertTrue(responseBody.contains("Error interno del servidor"));
    assertTrue(responseBody.contains("timestamp"));
  }

  @Test
  void testResponseContentTypeIsJson() {
    // Given
    var exception = new RuntimeException("Test");

    // When
    Response response = exceptionHandler.toResponse(exception);

    // Then
    String responseBody = response.getEntity().toString();
    assertTrue(responseBody.trim().startsWith("{"));
    assertTrue(responseBody.trim().endsWith("}"));
    assertTrue(responseBody.contains("\"error\""));
    assertTrue(responseBody.contains("\"timestamp\""));
  }
}
