package com.banco.abc.domain.validation;

import com.banco.abc.domain.exception.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * =============================================================
 * Class: DniValidator
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Validador de DNI
 * =============================================================
 */

@ApplicationScoped
public class DniValidator {

  public void validate(String dni) {
    if (dni == null || dni.trim().isEmpty()) {
      throw new ValidationException("El DNI es requerido");
    }

    if (!dni.matches("\\d{8}")) {
      throw new ValidationException("El DNI debe tener 8 dígitos numéricos");
    }
  }
}
