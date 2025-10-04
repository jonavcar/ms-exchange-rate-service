package com.banco.abc.domain.service;

import com.banco.abc.domain.exception.ExchangeRateException;
import com.banco.abc.domain.model.ExchangeRate;
import com.banco.abc.domain.port.ExchangeRatePort;
import com.banco.abc.domain.validation.DniValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDate;

@ApplicationScoped
public class ExchangeRateService {

    private final ExchangeRatePort exchangeRatePort;
    private final DniValidator dniValidator;

    @ConfigProperty(name = "consulta.diaria.limite")
    int limiteConsultasDiarias;

    @Inject
    public ExchangeRateService(ExchangeRatePort exchangeRatePort, DniValidator dniValidator) {
        this.exchangeRatePort = exchangeRatePort;
        this.dniValidator = dniValidator;
    }

    public ExchangeRate getExchangeRate(String dni) {
        dniValidator.validate(dni);
        validateDailyLimit(dni);

        ExchangeRate exchangeRate = exchangeRatePort.getExchangeRate();
        exchangeRatePort.registerQuery(dni, exchangeRate);

        return exchangeRate;
    }

    private void validateDailyLimit(String dni) {
        String today = LocalDate.now().toString();
        long dailyQueries = exchangeRatePort.getDailyQueriesCount(dni, today);

        if (dailyQueries >= limiteConsultasDiarias) {
            throw new ExchangeRateException("Se ha excedido el límite diario de consultas: " + limiteConsultasDiarias);
        }
    }
}
