package com.banco.abc.exchangerate.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * ExchangeRateException
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

public class ExchangeRateException extends WebApplicationException {
    public ExchangeRateException(String message, Response.Status status) {
        super(Response.status(status)
                .entity(String.format("{\"error\": \"%s\"}", message))
                .build());
    }

    public static ExchangeRateException limitExceeded(int limit) {
        return new ExchangeRateException(
                String.format("Ha excedido el límite de %d consultas diarias.", limit),
                Response.Status.TOO_MANY_REQUESTS
        );
    }

    public static ExchangeRateException externalApiError(String message) {
        return new ExchangeRateException(
                String.format("Error al consultar el tipo de cambio: %s", message),
                Response.Status.SERVICE_UNAVAILABLE
        );
    }
}