package com.banco.abc.exchangerate.proxy;

import com.banco.abc.exchangerate.model.ExchangeRateResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * ExchangeRateProxy
 * REST client for consuming external exchange rate API services.
 * Provides integration with third-party exchange rate data providers.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

@Path("/tipo-cambio")
@RegisterRestClient(configKey = "exchange-rate-api")
public interface ExchangeRateProxy {

  /**
   * Retrieves current exchange rates from external API.
   *
   * @return current exchange rate data
   */
  @GET
  @Path("/today.json")
  @Produces(MediaType.APPLICATION_JSON)
  ExchangeRateResponse getExchangeRate();
}