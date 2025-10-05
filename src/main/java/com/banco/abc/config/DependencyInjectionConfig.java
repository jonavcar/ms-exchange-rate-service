package com.banco.abc.config;

import com.banco.abc.domain.port.input.ExchangeRateInputPort;
import com.banco.abc.domain.port.output.ExchangeRateOutputPort;
import com.banco.abc.domain.port.output.QueryRegistryOutputPort;
import com.banco.abc.domain.service.ExchangeRateService;
import com.banco.abc.domain.validation.DniValidator;
import com.banco.abc.infrastructure.adapter.exchangerate.ExchangeRateAdapter;
import com.banco.abc.infrastructure.adapter.registry.QueryRegistryAdapter;
import com.banco.abc.infrastructure.client.rest.ExchangeRateClient;
import com.banco.abc.infrastructure.mapper.ExchangeRateInfraMapper;
import com.banco.abc.infrastructure.persistence.repository.QueryRegistryRepository;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

/**
 * =============================================================
 * Class: DependencyInjectionConfig
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * Configuración centralizada de inyección de dependencias para la arquitectura hexagonal.
 * Este componente conecta explícitamente los puertos (interfaces) con sus adaptadores
 * (implementaciones), siguiendo el principio de inversión de dependencias.
 * <p>
 * La ventaja de centralizar estas configuraciones es que hace explícitas las conexiones
 * entre componentes y facilita la sustitución de implementaciones, especialmente
 * útil para testing o cuando cambian los proveedores externos.
 * =============================================================
 */
@ApplicationScoped
public class DependencyInjectionConfig {

  private final ExchangeRateClient exchangeRateClient;
  private final ExchangeRateInfraMapper infraMapper;
  private final QueryRegistryRepository queryRegistryRepository;

  @Inject
  public DependencyInjectionConfig(
      ExchangeRateClient exchangeRateClient,
      ExchangeRateInfraMapper infraMapper,
      QueryRegistryRepository queryRegistryRepository) {
    this.exchangeRateClient = exchangeRateClient;
    this.infraMapper = infraMapper;
    this.queryRegistryRepository = queryRegistryRepository;
  }

  /**
   * Configura el puerto de salida para obtener tipos de cambio.
   * Conecta el puerto ExchangeRateOutputPort con su adaptador ExchangeRateAdapter.
   *
   * @return implementación del puerto de salida para tipos de cambio
   */
  @Produces
  @DefaultBean
  @ApplicationScoped
  public ExchangeRateOutputPort exchangeRateOutputPort() {
    return new ExchangeRateAdapter(exchangeRateClient, infraMapper);
  }

  /**
   * Configura el puerto de salida para el registro de consultas.
   * Conecta el puerto QueryRegistryOutputPort con su adaptador QueryRegistryAdapter.
   *
   * @return implementación del puerto de salida para registro de consultas
   */
  @Produces
  @DefaultBean
  @ApplicationScoped
  public QueryRegistryOutputPort queryRegistryOutputPort() {
    return new QueryRegistryAdapter(queryRegistryRepository, infraMapper);
  }

  /**
   * Configura el puerto de entrada para el servicio de tipo de cambio.
   * Conecta el puerto ExchangeRateInputPort con su implementación en el dominio ExchangeRateService.
   *
   * @param exchangeRateOutputPort  puerto de salida para tipos de cambio
   * @param queryRegistryOutputPort puerto de salida para registro de consultas
   * @param dniValidator            validador de DNI
   * @return implementación del puerto de entrada para el servicio de tipo de cambio
   */
  @Produces
  @DefaultBean
  @ApplicationScoped
  public ExchangeRateInputPort exchangeRateInputPort(
      ExchangeRateOutputPort exchangeRateOutputPort,
      QueryRegistryOutputPort queryRegistryOutputPort,
      DniValidator dniValidator) {
    return new ExchangeRateService(exchangeRateOutputPort, queryRegistryOutputPort, dniValidator);
  }
}
