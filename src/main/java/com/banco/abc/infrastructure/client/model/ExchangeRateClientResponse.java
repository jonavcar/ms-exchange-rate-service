package com.banco.abc.infrastructure.client.model;

/**
 * =============================================================
 * Class: ExchangeRateClientResponse
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * REST client response model for exchange rate data from external API.
 * =============================================================
 */
public record ExchangeRateClientResponse(
    String fecha,
    Double sunat,
    Double compra,
    Double venta
) {
}
