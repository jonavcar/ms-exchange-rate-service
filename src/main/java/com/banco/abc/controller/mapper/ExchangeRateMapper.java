package com.banco.abc.controller.mapper;

import com.banco.abc.controller.dto.ExchangeRateResponseDto;
import com.banco.abc.domain.model.ExchangeRate;
import org.mapstruct.Mapper;

/**
 * =============================================================
 * Class: ExchangeRateMapper
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * DTO para la respuesta del tipo de cambio
 * =============================================================
 */

@Mapper(componentModel = "cdi")
public interface ExchangeRateMapper {

  ExchangeRateResponseDto toDto(ExchangeRate domain);

}
