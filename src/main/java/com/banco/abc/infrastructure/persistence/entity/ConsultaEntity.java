package com.banco.abc.infrastructure.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultas")
public class ConsultaEntity extends PanacheEntity {
    public String dni;
    public String fechaConsulta;
    public Double sunat;
    public Double tipoCambioCompra;
    public Double tipoCambioVenta;
}
