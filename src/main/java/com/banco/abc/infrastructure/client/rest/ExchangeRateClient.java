package com.banco.abc.infrastructure.client.rest;

import com.banco.abc.domain.exception.TechnicalException;
import com.banco.abc.infrastructure.client.ExchangeRateRestClient;
import com.banco.abc.infrastructure.client.model.ExchangeRateClientResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * =============================================================
 * Class: ExchangeRateClient
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * REST client that connects to external API to get exchange rate data.
 * Uses MicroProfile Rest Client through an interface.
 * =============================================================
 */
@ApplicationScoped
public class ExchangeRateClient {

  @RestClient
  @Inject
  ExchangeRateRestClient exchangeRateRestClient;

  /**
   * Obtains exchange rate data from an external API.
   *
   * @return response with exchange rate data
   * @throws TechnicalException if there are errors in communication with the API
   */
  public ExchangeRateClientResponse getExchangeRateData() {
    try {
      return exchangeRateRestClient.getExchangeRateToday();
    } catch (Exception e) {
      throw new TechnicalException("Error getting exchange rate data: " + e.getMessage(), e);
    }
  }
}
