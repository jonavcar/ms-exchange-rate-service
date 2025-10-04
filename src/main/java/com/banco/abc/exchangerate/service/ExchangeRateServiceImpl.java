package com.banco.abc.exchangerate.service;

import com.banco.abc.exchangerate.exception.ExchangeRateException;
import com.banco.abc.exchangerate.model.ExchangeRateResponse;
import com.banco.abc.exchangerate.persistence.QueryRepository;
import com.banco.abc.exchangerate.proxy.ExchangeRateProxy;
import com.banco.abc.exchangerate.validation.DniValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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

  private final QueryRepository queryRepository;
  private final ExchangeRateProxy exchangeRateProxy;
  private final DniValidator dniValidator;
  @ConfigProperty(name = "daily.query.limit")
  int dailyQueryLimit;

  /**
   * Constructor with dependencies injected.
   *
   * @param queryRepository   repository for query persistence
   * @param exchangeRateProxy REST client for external API
   * @param dniValidator      validator for DNI
   */
  @Inject
  public ExchangeRateServiceImpl(
      QueryRepository queryRepository,
      @RestClient ExchangeRateProxy exchangeRateProxy,
      DniValidator dniValidator) {
    this.queryRepository = queryRepository;
    this.exchangeRateProxy = exchangeRateProxy;
    this.dniValidator = dniValidator;
  }

  /**
   * Retrieves current exchange rates with validation and rate limiting.
   *
   * @param dni validated ID
   * @return current exchange rates from external API
   */
  @Override
  @Transactional
  public ExchangeRateResponse getExchangeRate(String dni) {
    dniValidator.validate(dni);

    String today = java.time.LocalDate.now().toString();
    if (!validateDailyLimit(dni, today)) {
      throw ExchangeRateException.limitExceeded(dailyQueryLimit);
    }

    try {
      ExchangeRateResponse response = exchangeRateProxy.getExchangeRate();
      queryRepository.saveQuery(
          dni,
          response.getDate(),
          response.getSunatRate(),
          response.getBuyRate(),
          response.getSellRate()
      );
      ExchangeRateResponse minimalResponse = new ExchangeRateResponse();
      minimalResponse.setDate(response.getDate());
      minimalResponse.setSunatRate(response.getSunatRate());
      minimalResponse.setBuyRate(response.getBuyRate());
      minimalResponse.setSellRate(response.getSellRate());
      return minimalResponse;
    } catch (Exception e) {
      throw ExchangeRateException.externalApiError(e.getMessage());
    }
  }

  /**
   * Validates daily query limit for given DNI.
   *
   * @param dni  user identification
   * @param date query date
   * @return true if under limit, false otherwise
   */
  private boolean validateDailyLimit(String dni, String date) {
    long todayQueries = queryRepository.countQueriesByDniAndDate(dni, date);
    return todayQueries < dailyQueryLimit;
  }

}