package com.banco.abc.exchangerate.persistence;

import com.banco.abc.exchangerate.model.ConsultaRegistro;

import java.util.List;

/**
 * ConsultaRepositoryPort
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

public interface ConsultaRepositoryPort {
    long countConsultasByDniAndDate(String dni, String fecha);

    List<ConsultaRegistro> findByDni(String dni);

    void registrarConsulta(String dni, String fecha, Double sunat, Double tipoCambioCompra, Double tipoCambioVenta);
}