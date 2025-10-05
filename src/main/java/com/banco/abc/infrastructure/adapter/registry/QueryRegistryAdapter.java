package com.banco.abc.infrastructure.adapter.registry;

import com.banco.abc.domain.model.ExchangeRate;
import com.banco.abc.domain.port.output.QueryRegistryOutputPort;
import com.banco.abc.infrastructure.mapper.ExchangeRateInfraMapper;
import com.banco.abc.infrastructure.persistence.repository.QueryRegistryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;

/**
 * =============================================================
 * Class: QueryRegistryAdapter
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Adapter that implements QueryRegistryOutputPort.
 * Handles query registration and daily query counting.
 * =============================================================
 */
@ApplicationScoped
public class QueryRegistryAdapter implements QueryRegistryOutputPort {

  private final QueryRegistryRepository repository;
  private final ExchangeRateInfraMapper mapper;

  /**
   * Constructor with dependencies.
   */
  @Inject
  public QueryRegistryAdapter(QueryRegistryRepository repository, ExchangeRateInfraMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  @Transactional
  public void registerQuery(String dni, ExchangeRate exchangeRate) {
    var queryEntity = mapper.toQueryEntity(dni, exchangeRate, LocalDate.now());
    repository.persist(queryEntity);
  }

  @Override
  public long getDailyQueriesCount(String dni, String date) {
    return repository.countQueriesByDniAndDate(dni, date);
  }
}
