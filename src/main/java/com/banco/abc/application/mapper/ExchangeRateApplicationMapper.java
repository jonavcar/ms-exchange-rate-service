package com.banco.abc.application.mapper;

import com.banco.abc.application.dto.ExchangeRateApplicationDto;
import com.banco.abc.domain.model.ExchangeRate;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * =============================================================
 * Class: ExchangeRateApplicationMapper
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Mapper para convertir entre DTOs de aplicación y modelos de dominio.
 * =============================================================
 */
@ApplicationScoped
public class ExchangeRateApplicationMapper {

  /**
   * Convierte un modelo de dominio a un DTO de aplicación.
   *
   * @param exchangeRate modelo de dominio
   * @return DTO de aplicación
   */
  public ExchangeRateApplicationDto toApplicationDto(ExchangeRate exchangeRate) {
    return new ExchangeRateApplicationDto(
        exchangeRate.fecha(),
        exchangeRate.sunat(),
        exchangeRate.compra(),
        exchangeRate.venta()
    );
  }

  /**
   * Convierte un DTO de aplicación a un modelo de dominio.
   *
   * @param dto DTO de aplicación
   * @return modelo de dominio
   */
  public ExchangeRate toDomain(ExchangeRateApplicationDto dto) {
    return new ExchangeRate(
        dto.fecha(),
        dto.tasaSunat(),
        dto.tasaCompra(),
        dto.tasaVenta()
    );
  }
}
