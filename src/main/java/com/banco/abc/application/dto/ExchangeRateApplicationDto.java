package com.banco.abc.application.dto;

/**
 * =============================================================
 * Class: ExchangeRateApplicationDto
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * DTO específico de la capa de aplicación para manejar datos
 * de tipos de cambio entre capas de la aplicación.
 * =============================================================
 */
public record ExchangeRateApplicationDto(
    String fecha,
    Double tasaSunat,
    Double tasaCompra,
    Double tasaVenta
) {
}
