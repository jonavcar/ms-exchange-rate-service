package com.banco.abc.exchangerate.health;

import com.banco.abc.exchangerate.proxy.ExchangeRateProxy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * ExchangeRateHealthCheck
 * Health check implementation for monitoring external API availability.
 * Verifies external service connectivity for operational readiness.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */

@ApplicationScoped
@Readiness
public class ExchangeRateHealthCheck implements HealthCheck {

  @Inject
  @RestClient
  ExchangeRateProxy exchangeRateProxy;

  @Override
  public HealthCheckResponse call() {
    try {
      exchangeRateProxy.getExchangeRate();
      return HealthCheckResponse.up("API externa de tipo de cambio disponible");
    } catch (Exception e) {
      return HealthCheckResponse.down(
          "API externa de tipo de cambio no disponible: " + e.getMessage());
    }
  }
}
