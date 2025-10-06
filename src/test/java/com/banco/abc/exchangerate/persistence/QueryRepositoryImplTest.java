package com.banco.abc.exchangerate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.banco.abc.exchangerate.model.QueryRecord;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * QueryRepositoryImplTest
 * Integration tests for repository operations with in-memory database.
 * Tests persistence, queries, and data integrity for exchange rate records.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */
@QuarkusTest
class QueryRepositoryImplTest {

  @Inject
  QueryRepositoryImpl queryRepository;

  @Test
  @Transactional
  void testSaveQueryAndCount() {
    // Given
    var dni = "12345678";
    var date = LocalDate.now();
    var sunatRate = new BigDecimal("3.546");
    var buyRate = new BigDecimal("3.540");
    var sellRate = new BigDecimal("3.552");

    // When
    queryRepository.saveQuery(dni, date, sunatRate, buyRate, sellRate);

    // Then
    var count = queryRepository.countQueriesByDniAndDate(dni, date);
    assertEquals(1L, count);
  }

  @Test
  @Transactional
  void testFindByDni() {
    // Given
    var dni = "87654321";
    var date1 = LocalDate.now();
    var date2 = LocalDate.now().minusDays(1);

    // Save multiple records for the same DNI
    queryRepository.saveQuery(dni, date1, new BigDecimal("3.546"), new BigDecimal("3.540"),
        new BigDecimal("3.552"));
    queryRepository.saveQuery(dni, date2, new BigDecimal("3.500"), new BigDecimal("3.495"),
        new BigDecimal("3.505"));

    // When
    var records = queryRepository.findByDni(dni);

    // Then
    assertEquals(2, records.size());

    // Verify ordering (should be descending by date)
    assertTrue(records.get(0).getQueryDate().isAfter(records.get(1).getQueryDate()) ||
        records.get(0).getQueryDate().isEqual(records.get(1).getQueryDate()));
  }

  @Test
  @Transactional
  void testCountQueriesByDniAndDateWithNoRecords() {
    // Given
    var dni = "99999999";
    var date = LocalDate.now();

    // When
    var count = queryRepository.countQueriesByDniAndDate(dni, date);

    // Then
    assertEquals(0L, count);
  }

  @Test
  @Transactional
  void testMultipleSaveOperations() {
    // Given
    var dni = "11111111";
    var date = LocalDate.now();

    // When - save multiple queries for same DNI and date
    queryRepository.saveQuery(dni, date, new BigDecimal("3.546"), new BigDecimal("3.540"),
        new BigDecimal("3.552"));
    queryRepository.saveQuery(dni, date, new BigDecimal("3.548"), new BigDecimal("3.542"),
        new BigDecimal("3.554"));
    queryRepository.saveQuery(dni, date, new BigDecimal("3.550"), new BigDecimal("3.544"),
        new BigDecimal("3.556"));

    // Then
    var count = queryRepository.countQueriesByDniAndDate(dni, date);
    assertEquals(3L, count);

    var records = queryRepository.findByDni(dni);
    assertEquals(3, records.size());
  }

  @Test
  @Transactional
  void testDeleteOldRecords() {
    // Given
    var dni = "22222222";
    var oldDate = LocalDate.now().minusDays(30);
    var recentDate = LocalDate.now();

    // Save records with different dates
    queryRepository.saveQuery(dni, oldDate, new BigDecimal("3.500"), new BigDecimal("3.495"),
        new BigDecimal("3.505"));
    queryRepository.saveQuery(dni, recentDate, new BigDecimal("3.546"), new BigDecimal("3.540"),
        new BigDecimal("3.552"));

    // When - delete records older than 15 days
    var cutoffDate = LocalDate.now().minusDays(15);
    var deletedCount = queryRepository.deleteOldRecords(cutoffDate);

    // Then
    assertTrue(deletedCount >= 1); // At least the old record should be deleted

    // Verify recent record still exists
    var remainingCount = queryRepository.countQueriesByDniAndDate(dni, recentDate);
    assertEquals(1L, remainingCount);
  }

  @Test
  @Transactional
  void testFindByDniWithNoRecords() {
    // Given
    var dni = "33333333";

    // When
    var records = queryRepository.findByDni(dni);

    // Then
    assertNotNull(records);
    assertTrue(records.isEmpty());
  }

  @Test
  @Transactional
  void testSaveQueryWithNullValues() {
    // Given
    var dni = "44444444";
    var date = LocalDate.now();

    // When
    queryRepository.saveQuery(dni, date, null, null, null);

    // Then
    var count = queryRepository.countQueriesByDniAndDate(dni, date);
    assertEquals(1L, count);
    var records = queryRepository.findByDni(dni);
    assertEquals(1, records.size());

    QueryRecord record = records.get(0);
    assertEquals(dni, record.getDni());
    assertEquals(date, record.getQueryDate());
    assertNull(record.getSunatRate());
    assertNull(record.getBuyRate());
    assertNull(record.getSellRate());
  }
}

