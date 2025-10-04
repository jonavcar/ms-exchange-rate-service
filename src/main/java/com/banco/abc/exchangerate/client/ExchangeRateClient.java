package com.banco.abc.exchangerate.client;

import com.banco.abc.exchangerate.model.TipoCambioResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * ExchangeRateClient
 * <p>
 * For the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

@Path("/tipo-cambio")
@RegisterRestClient(configKey = "exchange-rate-api")
public interface ExchangeRateClient {

    @GET
    @Path("/today.json")
    @Produces(MediaType.APPLICATION_JSON)
    TipoCambioResponse getTipoCambio();
}