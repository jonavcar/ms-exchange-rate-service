package com.banco.abc.exchangerate.persistence;

import com.banco.abc.exchangerate.model.ConsultaRegistro;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * ConsultaRepository
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

@ApplicationScoped
public class ConsultaRepository implements ConsultaRepositoryPort, PanacheRepository<ConsultaRegistro> {

    public long countConsultasByDniAndDate(String dni, String fecha) {
        return count("dni = ?1 and fechaConsulta = ?2", dni, fecha);
    }

    public List<ConsultaRegistro> findByDni(String dni) {
        return list("dni", dni);
    }

    public void registrarConsulta(String dni, String fecha, Double sunat, Double tipoCambioCompra, Double tipoCambioVenta) {
        ConsultaRegistro registro = new ConsultaRegistro(dni, fecha, sunat, tipoCambioCompra, tipoCambioVenta);
        persist(registro);
    }
}