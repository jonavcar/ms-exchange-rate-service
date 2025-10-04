package com.banco.abc.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ExchangeRateResponse
 * Response model containing current exchange rate data from external API.
 * Maintains original JSON property names for service compatibility.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

public class ExchangeRateResponse {
  @JsonProperty("fecha")
  private String date;

  @JsonProperty("sunat")
  private Double sunatRate;

  @JsonProperty("compra")
  private Double buyRate;

  @JsonProperty("venta")
  private Double sellRate;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
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
