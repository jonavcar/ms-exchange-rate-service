package com.banco.abc.exchangerate.persistence;

import com.banco.abc.exchangerate.model.QueryRecord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * QueryRepositoryImpl
 * Repository implementation for exchange rate query persistence operations.
 * Uses Panache for simplified database access and query management.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

@ApplicationScoped
public class QueryRepositoryImpl implements QueryRepository, PanacheRepository<QueryRecord> {

  public long countQueriesByDniAndDate(String dni, String date) {
    return count("dni = ?1 and queryDate = ?2", dni, date);
  }

  public List<QueryRecord> findByDni(String dni) {
    return list("dni", dni);
  }

  public void saveQuery(String dni, String date, Double sunatRate, Double buyRate,
                        Double sellRate) {
    QueryRecord record = new QueryRecord(dni, date, sunatRate, buyRate, sellRate);
    persist(record);
  }

}