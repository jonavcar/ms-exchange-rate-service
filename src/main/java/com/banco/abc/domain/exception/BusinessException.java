package com.banco.abc.domain.exception;

/**
 * =============================================================
 * Class: BusinessException
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Excepción personalizada para errores de negocio
 * =============================================================
 */

public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }

}

