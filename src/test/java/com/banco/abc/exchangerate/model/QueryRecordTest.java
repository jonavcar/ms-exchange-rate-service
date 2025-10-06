package com.banco.abc.exchangerate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * QueryRecordTest
 * Unit tests for QueryRecord entity to verify constructor, getters and entity behavior.
 * Tests audit information and exchange rate data persistence functionality.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */
class QueryRecordTest {

  @Test
  void testConstructorWithAllParameters() {
    // Given
    var dni = "12345678";
    var queryDate = LocalDate.now();
    var sunatRate = new BigDecimal("3.546");
    var buyRate = new BigDecimal("3.540");
    var sellRate = new BigDecimal("3.552");

    // When
    var queryRecord = new QueryRecord(dni, queryDate, sunatRate, buyRate, sellRate);

    // Then
    assertEquals(dni, queryRecord.getDni());
    assertEquals(queryDate, queryRecord.getQueryDate());
    assertEquals(sunatRate, queryRecord.getSunatRate());
    assertEquals(buyRate, queryRecord.getBuyRate());
    assertEquals(sellRate, queryRecord.getSellRate());
  }

  @Test
  void testDefaultConstructor() {
    // When
    var queryRecord = new QueryRecord();

    // Then
    assertNull(queryRecord.getDni());
    assertNull(queryRecord.getQueryDate());
    assertNull(queryRecord.getSunatRate());
    assertNull(queryRecord.getBuyRate());
    assertNull(queryRecord.getSellRate());
  }

  @Test
  void testEntityInheritsPanacheEntity() {
    // Given
    var queryRecord = new QueryRecord();

    // Then - Verify it inherits from PanacheEntity (has id field)
    assertNotNull(queryRecord);
    // PanacheEntity provides id field automatically
    assertInstanceOf(PanacheEntity.class, queryRecord);
  }

  @Test
  void testConstructorWithNullValues() {
    // When
    var queryRecord = new QueryRecord(null, null, null, null, null);

    // Then
    assertNull(queryRecord.getDni());
    assertNull(queryRecord.getQueryDate());
    assertNull(queryRecord.getSunatRate());
    assertNull(queryRecord.getBuyRate());
    assertNull(queryRecord.getSellRate());
  }

  @Test
  void testConstructorWithSpecificDate() {
    // Given
    var dni = "87654321";
    var queryDate = LocalDate.of(2025, 10, 5);
    var sunatRate = new BigDecimal("3.500");
    var buyRate = new BigDecimal("3.495");
    var sellRate = new BigDecimal("3.505");

    // When
    var queryRecord = new QueryRecord(dni, queryDate, sunatRate, buyRate, sellRate);

    // Then
    assertEquals(dni, queryRecord.getDni());
    assertEquals(queryDate, queryRecord.getQueryDate());
    assertEquals(0, sunatRate.compareTo(queryRecord.getSunatRate()));
    assertEquals(0, buyRate.compareTo(queryRecord.getBuyRate()));
    assertEquals(0, sellRate.compareTo(queryRecord.getSellRate()));
  }
}
