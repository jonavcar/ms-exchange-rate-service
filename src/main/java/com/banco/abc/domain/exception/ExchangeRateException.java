package com.banco.abc.domain.exception;

/**
 * =============================================================
 * Class: ExchangeRateException
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Excepción personalizada para errores relacionados con el tipo de cambio
 * =============================================================
 */


public class ExchangeRateException extends RuntimeException {

  public ExchangeRateException(String message) {
    super(message);
  }

  public ExchangeRateException(String message, Throwable cause) {
    super(message, cause);
  }

}

