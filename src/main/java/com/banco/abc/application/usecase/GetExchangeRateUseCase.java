package com.banco.abc.application.usecase;

import com.banco.abc.controller.dto.ExchangeRateResponseDto;
import com.banco.abc.controller.mapper.ExchangeRateMapper;
import com.banco.abc.domain.port.input.ExchangeRateInputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * =============================================================
 * Class: GetExchangeRateUseCase
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Caso de uso para obtener el tipo de cambio actual.
 * Actúa como intermediario entre el controlador y el dominio,
 * aplicando la lógica de orquestación necesaria.
 * =============================================================
 */
@ApplicationScoped
public class GetExchangeRateUseCase {

  private final ExchangeRateInputPort exchangeRateInputPort;
  private final ExchangeRateMapper mapper;

  @Inject
  public GetExchangeRateUseCase(
      ExchangeRateInputPort exchangeRateInputPort,
      ExchangeRateMapper mapper) {
    this.exchangeRateInputPort = exchangeRateInputPort;
    this.mapper = mapper;
  }

  /**
   * Ejecuta el caso de uso para obtener el tipo de cambio.
   *
   * @param dni DNI del usuario que solicita el tipo de cambio
   * @return DTO con la información del tipo de cambio
   */
  public ExchangeRateResponseDto execute(String dni) {
    var exchangeRate = exchangeRateInputPort.getExchangeRate(dni);
    return mapper.toDto(exchangeRate);
  }
}
