package com.banco.abc.exchangerate.service;

import com.banco.abc.exchangerate.model.ExchangeRateResponse;

/**
 * ExchangeRateService
 * Service interface for exchange rate operations with validation and rate limiting.
 * Manages external API integration and query persistence for audit purposes.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

public interface ExchangeRateService {

  /**
   * Retrieves current exchange rates for a validated DNI.
   *
   * @param dni ID (8 digits required)
   * @return current exchange rates with date and rates
   * @throws ExchangeRateException if validation fails or daily limit exceeded
   */
  ExchangeRateResponse getExchangeRate(String dni);

}