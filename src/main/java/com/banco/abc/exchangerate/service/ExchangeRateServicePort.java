package com.banco.abc.exchangerate.service;

import com.banco.abc.exchangerate.model.TipoCambioResponse;

/**
 * ExchangeRateServicePort
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

public interface ExchangeRateServicePort {
    TipoCambioResponse consultarTipoCambio(String dni);
}