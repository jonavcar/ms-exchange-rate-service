package com.banco.abc.exchangerate.persistence;

import com.banco.abc.exchangerate.model.QueryRecord;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * QueryRepository
 * Repository interface for exchange rate query persistence operations.
 * Provides methods for counting, finding, and saving query records.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

public interface QueryRepository {

  /**
   * Counts queries by DNI and date for rate limiting validation.
   *
   * @param dni  user identification
   * @param date query date
   * @return number of queries found
   */
  long countQueriesByDniAndDate(String dni, LocalDate date);

  /**
   * Finds all query records for a specific DNI.
   *
   * @param dni user identification
   * @return list of query records ordered by date descending
   */
  List<QueryRecord> findByDni(String dni);

  /**
   * Saves a new query record for audit purposes.
   *
   * @param dni       user identification
   * @param date      query date
   * @param sunatRate SUNAT exchange rate
   * @param buyRate   purchase exchange rate
   * @param sellRate  selling exchange rate
   */
  void saveQuery(String dni, LocalDate date, BigDecimal sunatRate,
                 BigDecimal buyRate, BigDecimal sellRate);

  /**
   * Deletes old query records for data retention compliance.
   * Batch operation for efficient cleanup of historical data.
   *
   * @param cutoffDate records older than this date will be deleted
   * @return number of deleted records
   */
  long deleteOldRecords(LocalDate cutoffDate);
}