package com.banco.abc.infrastructure.mapper;

import com.banco.abc.domain.model.ExchangeRate;
import com.banco.abc.infrastructure.client.model.TipoCambioResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ExchangeRateInfraMapper {
    ExchangeRate toDomain(TipoCambioResponse response);
}
