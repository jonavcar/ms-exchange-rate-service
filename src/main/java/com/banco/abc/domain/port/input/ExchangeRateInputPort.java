package com.banco.abc.domain.port.input;

import com.banco.abc.domain.model.ExchangeRate;

/**
 * =============================================================
 * Interface: ExchangeRateInputPort
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Puerto de entrada que define las operaciones disponibles para
 * los adaptadores primarios (como controladores REST).
 * Este puerto es implementado por los servicios del dominio.
 * =============================================================
 */
public interface ExchangeRateInputPort {

  /**
   * Obtiene el tipo de cambio para un DNI específico.
   *
   * @param dni DNI del usuario que consulta el tipo de cambio
   * @return información del tipo de cambio
   */
  ExchangeRate getExchangeRate(String dni);
}
