package com.banco.abc.infrastructure.persistence.repository;

import com.banco.abc.infrastructure.persistence.entity.QueryRegistryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;

/**
 * =============================================================
 * Class: QueryRegistryRepository
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Repository for exchange rate query registry persistence.
 * =============================================================
 */
@ApplicationScoped
public class QueryRegistryRepository implements PanacheRepository<QueryRegistryEntity> {

  /**
   * Counts queries by DNI and date.
   */
  public long countQueriesByDniAndDate(String dni, String dateStr) {
    LocalDate queryDate = LocalDate.parse(dateStr);
    return count("dni = ?1 and queryDate = ?2", dni, queryDate);
  }
}
