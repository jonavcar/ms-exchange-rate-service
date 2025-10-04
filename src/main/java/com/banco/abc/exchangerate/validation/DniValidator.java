package com.banco.abc.exchangerate.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * DniValidator
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

@ApplicationScoped
public class DniValidator {
    private static final int DNI_LENGTH = 8;

    public void validate(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("{\"error\": \"El DNI es obligatorio\"}")
                            .build()
            );
        }

        if (!dni.matches("\\d{" + DNI_LENGTH + "}")) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("{\"error\": \"El DNI debe contener exactamente " + DNI_LENGTH + " dígitos\"}")
                            .build()
            );
        }
    }
}