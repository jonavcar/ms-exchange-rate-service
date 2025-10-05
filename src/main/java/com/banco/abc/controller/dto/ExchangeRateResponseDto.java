package com.banco.abc.controller.dto;

/**
 * =============================================================
 * Class: ExchangeRateResponseDto
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * DTO para la respuesta del tipo de cambio
 * =============================================================
 */

public record ExchangeRateResponseDto(
    String fecha,
    Double sunat,
    Double compra,
    Double venta
) {
}
