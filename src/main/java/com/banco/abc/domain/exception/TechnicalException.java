package com.banco.abc.domain.exception;

/**
 * =============================================================
 * Class: TechnicalException
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Excepción personalizada para errores técnicos
 * =============================================================
 */

public class TechnicalException extends RuntimeException {

  public TechnicalException(String message) {
    super(message);
  }

  public TechnicalException(String message, Throwable cause) {
    super(message, cause);
  }

}

