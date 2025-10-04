package com.banco.abc.infrastructure.adapter;

import com.banco.abc.domain.model.ExchangeRate;
import com.banco.abc.domain.port.ExchangeRatePort;
import com.banco.abc.infrastructure.client.ExchangeRateClient;
import com.banco.abc.infrastructure.mapper.ExchangeRateInfraMapper;
import com.banco.abc.infrastructure.persistence.repository.ConsultaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ExchangeRateAdapter implements ExchangeRatePort {

    private final ExchangeRateClient client;
    private final ConsultaRepository repository;
    private final ExchangeRateInfraMapper mapper;

    @Inject
    public ExchangeRateAdapter(
            @RestClient ExchangeRateClient client,
            ConsultaRepository repository,
            ExchangeRateInfraMapper mapper) {
        this.client = client;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ExchangeRate getExchangeRate() {
        return mapper.toDomain(client.getTipoCambio());
    }

    @Override
    @Transactional
    public void registerQuery(String dni, ExchangeRate exchangeRate) {
        repository.registrarConsulta(
                dni,
                exchangeRate.fecha(),
                exchangeRate.sunat(),
                exchangeRate.compra(),
                exchangeRate.venta()
        );
    }

    @Override
    @Transactional
    public long getDailyQueriesCount(String dni, String date) {
        return repository.countConsultasByDniAndDate(dni, date);
    }
}
