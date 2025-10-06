package com.banco.abc.exchangerate.persistence;

import com.banco.abc.exchangerate.model.QueryRecord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDate;
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

  /**
   * Counts queries by DNI and date for rate limiting validation.
   * Optimized query using indexed columns for better performance.
   *
   * @param dni  user identification
   * @param date query date
   * @return number of queries found
   */
  @Override
  public long countQueriesByDniAndDate(String dni, LocalDate date) {
    return count("dni = ?1 and queryDate = ?2", dni, date);
  }

  /**
   * Finds all query records for a specific DNI ordered by date.
   * Returns results ordered by date descending for better usability.
   *
   * @param dni user identification
   * @return list of query records ordered by date descending
   */
  @Override
  public List<QueryRecord> findByDni(String dni) {
    return list("dni = ?1 order by queryDate desc", dni);
  }

  /**
   * Saves a new query record for audit purposes.
   * Creates and persists a new record with all exchange rate data.
   *
   * @param dni       user identification
   * @param date      query date
   * @param sunatRate SUNAT exchange rate
   * @param buyRate   purchase exchange rate
   * @param sellRate  selling exchange rate
   */
  @Override
  public void saveQuery(String dni, LocalDate date, BigDecimal sunatRate,
                        BigDecimal buyRate, BigDecimal sellRate) {
    var record = new QueryRecord(dni, date, sunatRate, buyRate, sellRate);
    persist(record);
  }

  /**
   * Deletes old query records for data retention compliance.
   * Batch operation for efficient cleanup of historical data.
   *
   * @param cutoffDate records older than this date will be deleted
   * @return number of deleted records
   */
  public long deleteOldRecords(LocalDate cutoffDate) {
    return delete("queryDate < ?1", cutoffDate);
  }
}