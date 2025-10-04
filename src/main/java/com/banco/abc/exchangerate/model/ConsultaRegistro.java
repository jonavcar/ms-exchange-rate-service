package com.banco.abc.exchangerate.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * ConsultaRegistro
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

@Entity
@Table(name = "consultas_tipo_cambio")
public class ConsultaRegistro extends PanacheEntity {
    public String dni;
    public String fechaConsulta;
    public Double sunat;
    public Double tipoCambioCompra;
    public Double tipoCambioVenta;

    public ConsultaRegistro() {
    }

    public ConsultaRegistro(String dni, String fechaConsulta, Double sunat, Double tipoCambioCompra, Double tipoCambioVenta) {
        this.dni = dni;
        this.fechaConsulta = fechaConsulta;
        this.sunat = sunat;
        this.tipoCambioCompra = tipoCambioCompra;
        this.tipoCambioVenta = tipoCambioVenta;
    }
}