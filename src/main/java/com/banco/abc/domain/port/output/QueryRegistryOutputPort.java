package com.banco.abc.domain.port.output;

import com.banco.abc.domain.model.ExchangeRate;

/**
 * =============================================================
 * Interface: QueryRegistryOutputPort
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Puerto de salida específico para el registro y consulta de
 * las operaciones realizadas por los usuarios.
 * Permite separar la responsabilidad del registro de consultas
 * de la obtención de tipos de cambio.
 * =============================================================
 */
public interface QueryRegistryOutputPort {

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
