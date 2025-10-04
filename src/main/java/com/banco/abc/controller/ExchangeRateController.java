package com.banco.abc.controller;

import com.banco.abc.exchangerate.model.ExchangeRateResponse;
import com.banco.abc.exchangerate.service.ExchangeRateService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * ExchangeRateController
 * REST controller for exchange rate operations in Banco ABC microservice.
 * Provides secure endpoints with DNI validation and daily query limits.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

@Path("/exchange-rate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeRateController {

  private final ExchangeRateService exchangeRateService;

  /**
   * Constructor with dependencies injected.
   *
   * @param exchangeRateService service for exchange rate operations
   */
  @Inject
  public ExchangeRateController(ExchangeRateService exchangeRateService) {
    this.exchangeRateService = exchangeRateService;
  }

  /**
   * Retrieves current exchange rates for a given DNI.
   *
   * @param dni ID (8 digits required)
   * @return JSON response with exchange rates or error message
   */
  @GET
  public Response getExchangeRate(@QueryParam("dni") String dni) {
    try {
      ExchangeRateResponse response = exchangeRateService.getExchangeRate(dni);
      return Response.ok(response).build();
    } catch (WebApplicationException e) {
      return e.getResponse();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Internal server error\"}")
          .build();
    }
  }
}