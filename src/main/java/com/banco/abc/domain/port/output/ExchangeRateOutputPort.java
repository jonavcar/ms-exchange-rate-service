package com.banco.abc.domain.port.output;

import com.banco.abc.domain.model.ExchangeRate;

/**
 * =============================================================
 * Interface: ExchangeRateOutputPort
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Puerto de salida para obtener la información de tipo de cambio.
 * Define las operaciones que deben implementar los adaptadores secundarios
 * que obtienen los datos de tipo de cambio (ya sea desde APIs externas,
 * bases de datos, o cualquier otra fuente).
 * =============================================================
 */
public interface ExchangeRateOutputPort {

  /**
   * Obtiene la información de tipo de cambio actual.
   *
   * @return objeto con la información del tipo de cambio
   */
  ExchangeRate getExchangeRate();

  /**
   * Registra una consulta de tipo de cambio realizada por un usuario.
   *
   * @param dni          DNI del usuario que realizó la consulta
   * @param exchangeRate información del tipo de cambio consultado
   */
  void registerQuery(String dni, ExchangeRate exchangeRate);

  /**
   * Obtiene el número de consultas realizadas por un usuario en una fecha específica.
   *
   * @param dni  DNI del usuario
   * @param date fecha en formato String (YYYY-MM-DD)
   * @return número de consultas realizadas
   */
  long getDailyQueriesCount(String dni, String date);
}
