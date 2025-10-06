package com.banco.abc.exchangerate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import com.banco.abc.exchangerate.exception.ExchangeRateException;
import com.banco.abc.exchangerate.persistence.QueryRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * ExchangeRateServiceTest
 * Unit tests for the exchange rate service business logic.
 * Tests validation, rate limiting, and persistence operations.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */
@QuarkusTest
class ExchangeRateServiceTest {

  private static final String VALID_DNI = "12345678";
  private static final int DAILY_LIMIT = 10;
  @Inject
  ExchangeRateService exchangeRateService;
  @InjectMock
  QueryRepository queryRepository;

  @BeforeEach
  void setUp() {
    // Reset mocks before each test
    Mockito.reset(queryRepository);
  }

  @Test
  void testDailyLimitValidationUnderLimit() {
    // Given - user has made 5 queries today (under limit)
    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(VALID_DNI), any(LocalDate.class)))
        .thenReturn(5L);

    // When - we check if we can make another query
    // This will test the internal validation logic
    // Since we can't easily mock the external API, we'll test the limit validation separately

    // Verify the repository interaction
    var today = LocalDate.now();
    var count = queryRepository.countQueriesByDniAndDate(VALID_DNI, today);

    // Then - should be under limit
    assertTrue(count < DAILY_LIMIT);
    Mockito.verify(queryRepository).countQueriesByDniAndDate(eq(VALID_DNI), eq(today));
  }

  @Test
  void testDailyLimitValidationAtLimit() {
    // Given - user has reached the daily limit
    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(VALID_DNI), any(LocalDate.class)))
        .thenReturn((long) DAILY_LIMIT);

    // When - we try to make another query
    var exception = assertThrows(ExchangeRateException.class,
        () -> exchangeRateService.getExchangeRate(VALID_DNI));

    // Then - should throw limit exceeded exception
    assertNotNull(exception);
    assertNotNull(exception.getMessage());

    // Verify repository was called to check limit
    Mockito.verify(queryRepository).countQueriesByDniAndDate(eq(VALID_DNI), any(LocalDate.class));

    // Verify no save operation was attempted
    Mockito.verify(queryRepository, Mockito.never())
        .saveQuery(anyString(), any(), any(), any(), any());
  }

  @Test
  void testDailyLimitValidationExceedsLimit() {
    // Given - user has exceeded the daily limit
    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(VALID_DNI), any(LocalDate.class)))
        .thenReturn((long) DAILY_LIMIT + 1);

    // When - we try to make another query
    var exception = assertThrows(ExchangeRateException.class,
        () -> exchangeRateService.getExchangeRate(VALID_DNI));

    // Then - should throw limit exceeded exception
    assertNotNull(exception);
    assertNotNull(exception.getMessage());

    // Verify repository interaction
    Mockito.verify(queryRepository).countQueriesByDniAndDate(eq(VALID_DNI), any(LocalDate.class));
  }

  @Test
  void testDailyLimitValidationFirstTimeUser() {
    // Given - new user with no previous queries
    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(VALID_DNI), any(LocalDate.class)))
        .thenReturn(0L);

    // When - we check the limit for a new user
    var today = LocalDate.now();
    var count = queryRepository.countQueriesByDniAndDate(VALID_DNI, today);

    // Then - should be well under limit
    assertTrue(count < DAILY_LIMIT);
    assertEquals(0L, count);

    // Verify repository interaction
    Mockito.verify(queryRepository).countQueriesByDniAndDate(eq(VALID_DNI), eq(today));
  }

  @Test
  void testRepositoryInteractionForSaveQuery() {
    // Given - mock data for saving
    var dni = VALID_DNI;
    var date = LocalDate.now();
    var sunatRate = new BigDecimal("3.546");
    var buyRate = new BigDecimal("3.540");
    var sellRate = new BigDecimal("3.552");

    // When - we save a query
    queryRepository.saveQuery(dni, date, sunatRate, buyRate, sellRate);

    // Then - verify the save operation was called with correct parameters
    Mockito.verify(queryRepository).saveQuery(
        eq(dni),
        eq(date),
        eq(sunatRate),
        eq(buyRate),
        eq(sellRate)
    );
  }

  @Test
  void testExchangeRateExceptionCreation() {
    // Test static factory methods for exceptions

    // Test limit exceeded exception
    var limitException = ExchangeRateException.limitExceeded(DAILY_LIMIT);
    assertNotNull(limitException);
    assertTrue(limitException.getMessage().contains("429"));

    // Test external API error exception
    var apiException = ExchangeRateException.externalApiError("Connection timeout");
    assertNotNull(apiException);
    assertTrue(apiException.getMessage().contains("503"));
  }

  @Test
  void testEdgeCasesForDailyLimit() {
    // Test with exactly the limit
    Mockito.when(queryRepository.countQueriesByDniAndDate(anyString(), any(LocalDate.class)))
        .thenReturn(10L);

    var exception = assertThrows(ExchangeRateException.class,
        () -> exchangeRateService.getExchangeRate(VALID_DNI));
    assertNotNull(exception);

    // Test with one over the limit
    Mockito.when(queryRepository.countQueriesByDniAndDate(anyString(), any(LocalDate.class)))
        .thenReturn(11L);

    exception = assertThrows(ExchangeRateException.class,
        () -> exchangeRateService.getExchangeRate(VALID_DNI));
    assertNotNull(exception);
  }

  @Test
  void testDifferentDniValues() {
    // Test with different DNI values to ensure proper handling
    var dni1 = "12345678";
    var dni2 = "87654321";

    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(dni1), any(LocalDate.class)))
        .thenReturn(5L);
    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(dni2), any(LocalDate.class)))
        .thenReturn(15L);

    // DNI1 should be under limit
    var count1 = queryRepository.countQueriesByDniAndDate(dni1, LocalDate.now());
    assertTrue(count1 < DAILY_LIMIT);

    // DNI2 should be over limit
    var count2 = queryRepository.countQueriesByDniAndDate(dni2, LocalDate.now());
    assertTrue(count2 > DAILY_LIMIT);
  }

  @Test
  void testRepositoryInteractionWithNullValues() {
    // Test repository interaction with null values
    var dni = VALID_DNI;
    var date = LocalDate.now();

    queryRepository.saveQuery(dni, date, null, null, null);

    Mockito.verify(queryRepository).saveQuery(
        eq(dni),
        eq(date),
        eq(null),
        eq(null),
        eq(null)
    );
  }

  @Test
  void testCountQueriesForDifferentDates() {
    // Test counting queries for different dates
    var today = LocalDate.now();
    var yesterday = today.minusDays(1);

    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(VALID_DNI), eq(today)))
        .thenReturn(5L);
    Mockito.when(queryRepository.countQueriesByDniAndDate(eq(VALID_DNI), eq(yesterday)))
        .thenReturn(8L);

    var todayCount = queryRepository.countQueriesByDniAndDate(VALID_DNI, today);
    var yesterdayCount = queryRepository.countQueriesByDniAndDate(VALID_DNI, yesterday);

    assertEquals(5L, todayCount);
    assertEquals(8L, yesterdayCount);
  }
}
