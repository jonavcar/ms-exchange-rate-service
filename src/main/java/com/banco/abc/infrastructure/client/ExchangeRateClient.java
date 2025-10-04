package com.banco.abc.infrastructure.client;

import com.banco.abc.infrastructure.client.model.TipoCambioResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/tipo-cambio")
@RegisterRestClient(configKey = "exchange-rate-api")
public interface ExchangeRateClient {

    @GET
    @Path("/today.json")
    @Produces(MediaType.APPLICATION_JSON)
    TipoCambioResponse getTipoCambio();
}
