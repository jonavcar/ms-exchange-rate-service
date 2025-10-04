package com.banco.abc.exchangerate.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
@Table(name = "exchange_rate_queries")
public class QueryRecord extends PanacheEntity {

  @Column(name = "dni", nullable = false)
  private String dni;

  @Column(name = "query_date", nullable = false)
  private String queryDate;

  @Column(name = "sunat_rate")
  private Double sunatRate;

  @Column(name = "buy_rate")
  private Double buyRate;

  @Column(name = "sell_rate")
  private Double sellRate;

  public QueryRecord() {
  }

  public QueryRecord(String dni, String queryDate, Double sunatRate, Double buyRate,
                     Double sellRate) {
    this.dni = dni;
    this.queryDate = queryDate;
    this.sunatRate = sunatRate;
    this.buyRate = buyRate;
    this.sellRate = sellRate;
  }

  // Getters and Setters
  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getQueryDate() {
    return queryDate;
  }

  public void setQueryDate(String queryDate) {
    this.queryDate = queryDate;
  }

  public Double getSunatRate() {
    return sunatRate;
  }

  public void setSunatRate(Double sunatRate) {
    this.sunatRate = sunatRate;
  }

  public Double getBuyRate() {
    return buyRate;
  }

  public void setBuyRate(Double buyRate) {
    this.buyRate = buyRate;
  }

  public Double getSellRate() {
    return sellRate;
  }

  public void setSellRate(Double sellRate) {
    this.sellRate = sellRate;
  }
}