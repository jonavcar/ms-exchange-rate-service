package com.banco.abc.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ExchangeRateResponse
 * Response model containing current exchange rate data from external API.
 * Maintains original JSON property names for service compatibility.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

public record ExchangeRateResponse(
    @JsonProperty("fecha") LocalDate date,
    @JsonProperty("sunat") BigDecimal sunatRate,
    @JsonProperty("compra") BigDecimal buyRate,
    @JsonProperty("venta") BigDecimal sellRate
) {
}
