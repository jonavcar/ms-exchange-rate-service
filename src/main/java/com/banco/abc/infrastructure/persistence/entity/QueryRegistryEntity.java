package com.banco.abc.infrastructure.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * =============================================================
 * Class: QueryRegistryEntity
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Persistence entity for exchange rate query registry.
 * =============================================================
 */
@Entity
@Table(name = "query_registry")
public class QueryRegistryEntity extends PanacheEntity {

  public String dni;
  public LocalDate queryDate;
  public String exchangeRateDate;
  public Double exchangeRateSunat;
  public Double exchangeRateCompra;
  public Double exchangeRateVenta;

  /**
   * Default constructor for JPA.
   */
  public QueryRegistryEntity() {
  }

  /**
   * Constructor with all fields.
   */
  public QueryRegistryEntity(String dni, LocalDate queryDate,
                             String exchangeRateDate, Double exchangeRateSunat,
                             Double exchangeRateCompra, Double exchangeRateVenta) {
    this.dni = dni;
    this.queryDate = queryDate;
    this.exchangeRateDate = exchangeRateDate;
    this.exchangeRateSunat = exchangeRateSunat;
    this.exchangeRateCompra = exchangeRateCompra;
    this.exchangeRateVenta = exchangeRateVenta;
  }
}
