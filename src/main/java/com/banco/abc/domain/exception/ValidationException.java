package com.banco.abc.domain.exception;

/**
 * =============================================================
 * Class: ValidationException
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Excepción personalizada para errores de validación
 * =============================================================
 */

public class ValidationException extends RuntimeException {

  public ValidationException(String message) {
    super(message);
  }

}

