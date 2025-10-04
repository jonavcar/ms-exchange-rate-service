package com.banco.abc.domain.model;

/**
 * Modelo de dominio para el tipo de cambio
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */
public record ExchangeRate(
        String fecha,
        Double sunat,
        Double compra,
        Double venta
) {
}
