package com.banco.abc.exchangerate.service;

import com.banco.abc.exchangerate.exception.ExchangeRateException;
import com.banco.abc.exchangerate.model.ExchangeRateResponse;
import com.banco.abc.exchangerate.persistence.QueryRepository;
import com.banco.abc.exchangerate.proxy.ExchangeRateProxy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 * ExchangeRateServiceImpl
 * Service implementation handling exchange rate queries with business validation.
 * Integrates external APIs, enforces daily limits, and persists audit records.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

@ApplicationScoped
public class ExchangeRateServiceImpl implements ExchangeRateService {

  private static final Logger LOG = Logger.getLogger(ExchangeRateServiceImpl.class);

  @Inject
  QueryRepository queryRepository;

  @Inject
  @RestClient
  ExchangeRateProxy exchangeRateProxy;

  @ConfigProperty(name = "daily.query.limit")
  int dailyQueryLimit;

  /**
   * Retrieves current exchange rates with validation and rate limiting.
   * Implements comprehensive error handling and audit trail.
   *
   * @param dni validated ID
   * @return current exchange rates from external API
   */
  @Override
  @Transactional
  public ExchangeRateResponse getExchangeRate(String dni) {
    var today = LocalDate.now();

    if (!isWithinDailyLimit(dni, today)) {
      LOG.warnf("Limite diario excedido para DNI: %s", dni);
      throw ExchangeRateException.limitExceeded(dailyQueryLimit);
    }

    try {
      var response = exchangeRateProxy.getExchangeRate();
      queryRepository.saveQuery(
          dni,
          response.date(),
          response.sunatRate(),
          response.buyRate(),
          response.sellRate()
      );

      LOG.infof("Consulta exitosa para DNI: %s", dni);
      return response;
    } catch (Exception e) {
      LOG.errorf(e, "Error consultando API externa para DNI: %s", dni);
      throw ExchangeRateException.externalApiError(e.getMessage());
    }
  }

  /**
   * Validates daily query limit using pattern matching for switch expressions.
   * More readable and efficient than traditional if-else chains.
   *
   * @param dni  user identification
   * @param date query date
   * @return true if under limit, false otherwise
   */
  private boolean isWithinDailyLimit(String dni, LocalDate date) {
    var todayQueries = queryRepository.countQueriesByDniAndDate(dni, date);

    return switch (Long.compare(todayQueries, dailyQueryLimit)) {
      case -1 -> {
        LOG.debugf("DNI %s tiene %d consultas, bajo el limite de %d", dni, todayQueries,
            dailyQueryLimit);
        yield true;
      }
      case 0, 1 -> {
        LOG.debugf("DNI %s ha alcanzado/excedido el limite: %d/%d", dni, todayQueries,
            dailyQueryLimit);
        yield false;
      }
      default -> false;
    };
  }

}