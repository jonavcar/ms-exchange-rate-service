package com.banco.abc.infrastructure.adapter.exchangerate;

import com.banco.abc.domain.model.ExchangeRate;
import com.banco.abc.domain.port.output.ExchangeRateOutputPort;
import com.banco.abc.infrastructure.client.rest.ExchangeRateClient;
import com.banco.abc.infrastructure.mapper.ExchangeRateInfraMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * =============================================================
 * Class: ExchangeRateAdapter
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Adapter that implements ExchangeRateOutputPort.
 * Uses REST client to get exchange rate data from external API.
 * =============================================================
 */
@ApplicationScoped
public class ExchangeRateAdapter implements ExchangeRateOutputPort {

  private final ExchangeRateClient exchangeRateClient;
  private final ExchangeRateInfraMapper infraMapper;

  /**
   * Constructor with dependencies.
   */
  @Inject
  public ExchangeRateAdapter(ExchangeRateClient exchangeRateClient,
                             ExchangeRateInfraMapper infraMapper) {
    this.exchangeRateClient = exchangeRateClient;
    this.infraMapper = infraMapper;
  }

  @Override
  public ExchangeRate getExchangeRate() {
    var clientResponse = exchangeRateClient.getExchangeRateData();
    return infraMapper.toDomain(clientResponse);
  }

  @Override
  public void registerQuery(String dni, ExchangeRate exchangeRate) {
    throw new UnsupportedOperationException(
        "This operation should be handled by QueryRegistryAdapter");
  }

  @Override
  public long getDailyQueriesCount(String dni, String date) {
    throw new UnsupportedOperationException(
        "This operation should be handled by QueryRegistryAdapter");
  }
}
