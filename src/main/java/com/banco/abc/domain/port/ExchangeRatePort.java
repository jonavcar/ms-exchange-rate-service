package com.banco.abc.domain.port;

import com.banco.abc.domain.model.ExchangeRate;

/**
 * Puerto del dominio para el servicio de tipo de cambio
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */
public interface ExchangeRatePort {
    ExchangeRate getExchangeRate();

    void registerQuery(String dni, ExchangeRate exchangeRate);

    long getDailyQueriesCount(String dni, String date);
}
