package com.banco.abc.exchangerate.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * QueryRecord
 * Entity for persisting exchange rate query records with audit information.
 * Stores DNI, query date, and exchange rate data for compliance tracking.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

@Entity
@Table(name = "exchange_rate_queries", indexes = {
    @Index(name = "idx_dni_date", columnList = "dni, query_date"),
    @Index(name = "idx_query_date", columnList = "query_date")
})
public class QueryRecord extends PanacheEntity {

  @Column(name = "dni", nullable = false, length = 8)
  private String dni;

  @Column(name = "query_date", nullable = false)
  private LocalDate queryDate;

  @Column(name = "sunat_rate", precision = 10, scale = 4)
  private BigDecimal sunatRate;

  @Column(name = "buy_rate", precision = 10, scale = 4)
  private BigDecimal buyRate;

  @Column(name = "sell_rate", precision = 10, scale = 4)
  private BigDecimal sellRate;

  /**
   * Default constructor for JPA.
   */
  public QueryRecord() {
  }

  /**
   * Constructor to initialize all fields.
   *
   * @param dni       user identification
   * @param queryDate date of the query
   * @param sunatRate SUNAT exchange rate
   * @param buyRate   purchase exchange rate
   * @param sellRate  selling exchange rate
   */
  public QueryRecord(String dni, LocalDate queryDate, BigDecimal sunatRate, BigDecimal buyRate,
                     BigDecimal sellRate) {
    this.dni = dni;
    this.queryDate = queryDate;
    this.sunatRate = sunatRate;
    this.buyRate = buyRate;
    this.sellRate = sellRate;
  }

  // Getters necesarios para JPA y acceso de datos
  public String getDni() {
    return dni;
  }

  public LocalDate getQueryDate() {
    return queryDate;
  }

  public BigDecimal getSunatRate() {
    return sunatRate;
  }

  public BigDecimal getBuyRate() {
    return buyRate;
  }

  public BigDecimal getSellRate() {
    return sellRate;
  }
}