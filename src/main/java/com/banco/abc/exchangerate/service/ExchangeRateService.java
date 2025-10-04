package com.banco.abc.exchangerate.service;

import com.banco.abc.exchangerate.client.ExchangeRateClient;
import com.banco.abc.exchangerate.exception.ExchangeRateException;
import com.banco.abc.exchangerate.model.TipoCambioResponse;
import com.banco.abc.exchangerate.persistence.ConsultaRepositoryPort;
import com.banco.abc.exchangerate.validation.DniValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * ExchangeRateService
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

@ApplicationScoped
public class ExchangeRateService implements ExchangeRateServicePort {

    private final ConsultaRepositoryPort consultaRepository;
    private final ExchangeRateClient exchangeRateClient;
    private final DniValidator dniValidator;
    @ConfigProperty(name = "consulta.diaria.limite")
    int limiteConsultasDiarias;

    @Inject
    public ExchangeRateService(
            ConsultaRepositoryPort consultaRepository,
            @RestClient ExchangeRateClient exchangeRateClient,
            DniValidator dniValidator) {
        this.consultaRepository = consultaRepository;
        this.exchangeRateClient = exchangeRateClient;
        this.dniValidator = dniValidator;
    }

    @Override
    @Transactional
    public TipoCambioResponse consultarTipoCambio(String dni) {
        dniValidator.validate(dni);

        String fechaHoy = java.time.LocalDate.now().toString();
        if (!validarLimiteConsultas(dni, fechaHoy)) {
            throw ExchangeRateException.limitExceeded(limiteConsultasDiarias);
        }

        try {
            TipoCambioResponse response = exchangeRateClient.getTipoCambio();
            consultaRepository.registrarConsulta(
                    dni,
                    response.getFecha(),
                    response.getSunat(),
                    response.getCompra(),
                    response.getVenta()
            );
            TipoCambioResponse minimalResponse = new TipoCambioResponse();
            minimalResponse.setFecha(response.getFecha());
            minimalResponse.setSunat(response.getSunat());
            minimalResponse.setCompra(response.getCompra());
            minimalResponse.setVenta(response.getVenta());
            return minimalResponse;
        } catch (Exception e) {
            throw ExchangeRateException.externalApiError(e.getMessage());
        }
    }

    private boolean validarLimiteConsultas(String dni, String fecha) {
        long consultasHoy = consultaRepository.countConsultasByDniAndDate(dni, fecha);
        return consultasHoy < limiteConsultasDiarias;
    }
}