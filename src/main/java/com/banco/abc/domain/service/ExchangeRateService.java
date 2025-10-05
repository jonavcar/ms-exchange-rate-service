package com.banco.abc.domain.service;

import com.banco.abc.domain.exception.ExchangeRateException;
import com.banco.abc.domain.model.ExchangeRate;
import com.banco.abc.domain.port.input.ExchangeRateInputPort;
import com.banco.abc.domain.port.output.ExchangeRateOutputPort;
import com.banco.abc.domain.port.output.QueryRegistryOutputPort;
import com.banco.abc.domain.validation.DniValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * =============================================================
 * Class: ExchangeRateService
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Implementación del servicio de dominio para el tipo de cambio.
 * Implementa el puerto de entrada ExchangeRateInputPort y utiliza
 * los puertos de salida para obtener datos y registrar consultas.
 * =============================================================
 */

@ApplicationScoped
public class ExchangeRateService implements ExchangeRateInputPort {

  private final ExchangeRateOutputPort exchangeRateOutputPort;
  private final QueryRegistryOutputPort queryRegistryOutputPort;
  private final DniValidator dniValidator;

  @ConfigProperty(name = "consulta.diaria.limite")
  int dailyQueryLimit;

  @Inject
  public ExchangeRateService(
      ExchangeRateOutputPort exchangeRateOutputPort,
      QueryRegistryOutputPort queryRegistryOutputPort,
      DniValidator dniValidator) {
    this.exchangeRateOutputPort = exchangeRateOutputPort;
    this.queryRegistryOutputPort = queryRegistryOutputPort;
    this.dniValidator = dniValidator;
  }

  @Override
  public ExchangeRate getExchangeRate(String dni) {
    dniValidator.validate(dni);
    validateDailyLimit(dni);

    ExchangeRate exchangeRate = exchangeRateOutputPort.getExchangeRate();
    queryRegistryOutputPort.registerQuery(dni, exchangeRate);

    return exchangeRate;
  }

  private void validateDailyLimit(String dni) {
    String today = LocalDate.now().toString();
    long dailyQueries = queryRegistryOutputPort.getDailyQueriesCount(dni, today);

    if (dailyQueries >= dailyQueryLimit) {
      throw new ExchangeRateException(
          "Se ha excedido el límite diario de consultas: " + dailyQueryLimit);
    }
  }
}
