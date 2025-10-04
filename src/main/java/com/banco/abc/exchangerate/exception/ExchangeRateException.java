package com.banco.abc.exchangerate.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * ExchangeRateException
 * Custom exception for exchange rate service operations.
 * Provides standardized error responses for validation and API failures.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

public class ExchangeRateException extends WebApplicationException {

  /**
   * Constructs a new ExchangeRateException with the specified detail message and HTTP status.
   *
   * @param message the detail message
   * @param status  the HTTP status code
   */
  public ExchangeRateException(String message, Response.Status status) {
    super(Response.status(status)
        .entity(String.format("{\"error\": \"%s\"}", message))
        .build());
  }

  /**
   * Creates exception for daily query limit exceeded.
   *
   * @param limit the daily query limit
   * @return configured exception with 429 status
   */
  public static ExchangeRateException limitExceeded(int limit) {
    return new ExchangeRateException(
        String.format("You have exceeded the limit of %d daily queries.", limit),
        Response.Status.TOO_MANY_REQUESTS
    );
  }

  /**
   * Creates exception for external API errors.
   *
   * @param message the error message from external API
   * @return configured exception with 503 status
   */
  public static ExchangeRateException externalApiError(String message) {
    return new ExchangeRateException(
        String.format("Error querying exchange rate: %s", message),
        Response.Status.SERVICE_UNAVAILABLE
    );
  }
}