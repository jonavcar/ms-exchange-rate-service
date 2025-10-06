package com.banco.abc.exchangerate.health;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banco.abc.exchangerate.model.ExchangeRateResponse;
import com.banco.abc.exchangerate.proxy.ExchangeRateProxy;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * ExchangeRateHealthCheckTest
 * Unit tests for health check functionality to verify external API dependency status.
 * Tests readiness probe behavior for monitoring and alerting systems.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */
@ExtendWith(MockitoExtension.class)
class ExchangeRateHealthCheckTest {

  @Mock
  private ExchangeRateProxy exchangeRateProxy;

  @InjectMocks
  private ExchangeRateHealthCheck healthCheck;

  private ExchangeRateResponse mockResponse;

  @BeforeEach
  void setUp() {
    mockResponse = new ExchangeRateResponse(
        LocalDate.now(),
        new BigDecimal("3.546"),
        new BigDecimal("3.540"),
        new BigDecimal("3.552")
    );
  }

  @Test
  void testHealthCheckWhenExternalApiIsUp() {
    // Given
    when(exchangeRateProxy.getExchangeRate()).thenReturn(mockResponse);

    // When
    HealthCheckResponse response = healthCheck.call();

    // Then
    assertEquals(HealthCheckResponse.Status.UP, response.getStatus());
    assertEquals("API externa de tipo de cambio disponible", response.getName());
    verify(exchangeRateProxy, times(1)).getExchangeRate();
  }

  @Test
  void testHealthCheckWhenExternalApiIsDown() {
    // Given
    String errorMessage = "Connection timeout";
    when(exchangeRateProxy.getExchangeRate())
        .thenThrow(new RuntimeException(errorMessage));

    // When
    HealthCheckResponse response = healthCheck.call();

    // Then
    assertEquals(HealthCheckResponse.Status.DOWN, response.getStatus());
    assertTrue(response.getName().contains("API externa de tipo de cambio no disponible"));
    assertTrue(response.getName().contains(errorMessage));
    verify(exchangeRateProxy, times(1)).getExchangeRate();
  }

  @Test
  void testHealthCheckWithNullPointerException() {
    // Given
    when(exchangeRateProxy.getExchangeRate())
        .thenThrow(new NullPointerException("Null response"));

    // When
    HealthCheckResponse response = healthCheck.call();

    // Then
    assertEquals(HealthCheckResponse.Status.DOWN, response.getStatus());
    assertTrue(response.getName().contains("API externa de tipo de cambio no disponible"));
    verify(exchangeRateProxy, times(1)).getExchangeRate();
  }

  @Test
  void testHealthCheckWithGenericException() {
    // Given
    when(exchangeRateProxy.getExchangeRate())
        .thenThrow(new IllegalStateException("Service unavailable"));

    // When
    HealthCheckResponse response = healthCheck.call();

    // Then
    assertEquals(HealthCheckResponse.Status.DOWN, response.getStatus());
    assertTrue(response.getName().contains("Service unavailable"));
    verify(exchangeRateProxy, times(1)).getExchangeRate();
  }
}
