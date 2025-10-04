package com.banco.abc.exchangerate.persistence;

import com.banco.abc.exchangerate.model.QueryRecord;
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
  long countQueriesByDniAndDate(String dni, String date);

  /**
   * Finds all query records for a specific DNI.
   *
   * @param dni user identification
   * @return list of query records
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
  void saveQuery(String dni, String date, Double sunatRate, Double buyRate, Double sellRate);
}