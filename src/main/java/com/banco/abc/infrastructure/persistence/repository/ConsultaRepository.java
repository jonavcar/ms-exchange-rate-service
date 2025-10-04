package com.banco.abc.infrastructure.persistence.repository;

import com.banco.abc.infrastructure.persistence.entity.ConsultaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsultaRepository implements PanacheRepository<ConsultaEntity> {

    public long countConsultasByDniAndDate(String dni, String fecha) {
        return count("dni = ?1 and fechaConsulta = ?2", dni, fecha);
    }

    public void registrarConsulta(String dni, String fecha, Double sunat, Double compra, Double venta) {
        ConsultaEntity consulta = new ConsultaEntity();
        consulta.dni = dni;
        consulta.fechaConsulta = fecha;
        consulta.sunat = sunat;
        consulta.tipoCambioCompra = compra;
        consulta.tipoCambioVenta = venta;
        persist(consulta);
    }
}
