package com.banco.abc.infrastructure.client;

import com.banco.abc.infrastructure.client.model.ExchangeRateClientResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * =============================================================
 * Interface: ExchangeRateRestClient
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * REST client interface that connects to external API to get
 * current exchange rate data. Uses MicroProfile Rest Client.
 * =============================================================
 */
@RegisterRestClient(configKey = "exchange-rate-api")
@Path("/tipo-cambio")
public interface ExchangeRateRestClient {

  /**
   * Gets today's exchange rate data from external API.
   */
  @GET
  @Path("/today.json")
  @Produces(MediaType.APPLICATION_JSON)
  ExchangeRateClientResponse getExchangeRateToday();
}
