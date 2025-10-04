package com.banco.abc.exchangerate.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * DniValidator
 * Validates DNI format and requirements for exchange rate queries.
 * Ensures DNI contains exactly 8 numeric digits before processing requests.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

@ApplicationScoped
public class DniValidator {
  private static final int DNI_LENGTH = 8;

  /**
   * Validates DNI format and throws exception if invalid.
   *
   * @param dni the DNI to validate
   * @throws WebApplicationException if DNI is null, empty, or invalid format
   */
  public void validate(String dni) {
    if (dni == null || dni.trim().isEmpty()) {
      throw new WebApplicationException(
          Response.status(Response.Status.BAD_REQUEST)
              .entity("{\"error\": \"DNI is required\"}")
              .build()
      );
    }

    if (!dni.matches("\\d{" + DNI_LENGTH + "}")) {
      throw new WebApplicationException(
          Response.status(Response.Status.BAD_REQUEST)
              .entity(
                  "{\"error\": \"DNI must contain exactly " + DNI_LENGTH + " digits\"}")
              .build()
      );
    }
  }
}