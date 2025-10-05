package com.banco.abc.controller;

import com.banco.abc.application.usecase.GetExchangeRateUseCase;
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
 * =============================================================
 * Class: ExchangeRateController
 * Author: Joaquin Navarro C.
 * Date: 2025-10-05
 * -------------------------------------------------------------
 * Project: Exchange Rate Service microservice for Banco ABC
 * -------------------------------------------------------------
 * Description:
 * This microservice provides exchange rate operations for Banco ABC.
 * It follows Hexagonal Architecture principles using Quarkus, organizing
 * the codebase into clear layers:
 * - Controller: Acts as an adapter primary (entrada) that handles API requests.
 * - Application: Contains use cases that orchestrate the business flow.
 * - Domain: Contains business logic, models, and ports (interfaces).
 * - Infrastructure: Implements adapters (secondary) for external systems.
 * =============================================================
 */

@Path("/exchange-rate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeRateController {

  private final GetExchangeRateUseCase getExchangeRateUseCase;

  /**
   * Creates an instance of ExchangeRateController.
   *
   * @param getExchangeRateUseCase the use case for getting exchange rate
   */
  @Inject
  public ExchangeRateController(GetExchangeRateUseCase getExchangeRateUseCase) {
    this.getExchangeRateUseCase = getExchangeRateUseCase;
  }

  /**
   * Gets the exchange rate for a given DNI.
   *
   * @param dni the DNI to query
   * @return the exchange rate response DTO
   */
  @GET
  public Response getExchangeRate(@QueryParam("dni") String dni) {
    try {
      var responseDto = getExchangeRateUseCase.execute(dni);
      return Response.ok(responseDto).build();
    } catch (WebApplicationException e) {
      return e.getResponse();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"" + e.getMessage() + "\"}")
          .build();
    }
  }
}