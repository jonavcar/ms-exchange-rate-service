package com.banco.abc.controller.dto;

/**
 * DTO para la respuesta del tipo de cambio
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */
public record ExchangeRateResponseDTO(
        String fecha,
        Double sunat,
        Double compra,
        Double venta
) {
}
