package com.banco.abc.controller.mapper;

import com.banco.abc.controller.dto.ExchangeRateResponseDTO;
import com.banco.abc.domain.model.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ExchangeRateMapper {
    ExchangeRateResponseDTO toDto(ExchangeRate domain);
}
