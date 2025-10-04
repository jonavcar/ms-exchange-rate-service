package com.banco.abc.infrastructure.client.model;

public record TipoCambioResponse(
        String fecha,
        Double sunat,
        Double compra,
        Double venta
) {
}
