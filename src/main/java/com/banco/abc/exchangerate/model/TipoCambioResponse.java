package com.banco.abc.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TipoCambioResponse
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

public class TipoCambioResponse {
    @JsonProperty("fecha")
    private String fecha;

    @JsonProperty("sunat")
    private Double sunat;

    @JsonProperty("compra")
    private Double compra;

    @JsonProperty("venta")
    private Double venta;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getSunat() {
        return sunat;
    }

    public void setSunat(Double sunat) {
        this.sunat = sunat;
    }

    public Double getCompra() {
        return compra;
    }

    public void setCompra(Double compra) {
        this.compra = compra;
    }

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }
}