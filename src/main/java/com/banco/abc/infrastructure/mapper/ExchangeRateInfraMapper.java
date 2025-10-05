package com.banco.abc.infrastructure.mapper;

import com.banco.abc.domain.model.ExchangeRate;
import com.banco.abc.infrastructure.client.model.ExchangeRateClientResponse;
import com.banco.abc.infrastructure.persistence.entity.QueryRegistryEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;

/**
 * =============================================================
 * Class: ExchangeRateInfraMapper
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Infrastructure mapper for converting between infrastructure models
 * and domain models.
 * =============================================================
 */
@ApplicationScoped
public class ExchangeRateInfraMapper {

  /**
   * Maps external API response to domain model.
   */
  public ExchangeRate toDomain(ExchangeRateClientResponse response) {
    return new ExchangeRate(
        response.fecha(),
        response.sunat(),
        response.compra(),
        response.venta()
    );
  }

  /**
   * Maps domain model to query entity for persistence.
   */
  public QueryRegistryEntity toQueryEntity(String dni, ExchangeRate exchangeRate,
                                           LocalDate queryDate) {
    return new QueryRegistryEntity(
        dni,
        queryDate,
        exchangeRate.fecha(),
        exchangeRate.sunat(),
        exchangeRate.compra(),
        exchangeRate.venta()
    );
  }
}
