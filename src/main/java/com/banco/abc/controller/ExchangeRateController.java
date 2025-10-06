package com.banco.abc.controller;

import com.banco.abc.exchangerate.model.ExchangeRateResponse;
import com.banco.abc.exchangerate.service.ExchangeRateService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

/**
 * ExchangeRateController
 * REST controller for exchange rate operations in Banco ABC microservice.
 * Provides secure endpoints with DNI validation and daily query limits.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-03
 */

@Path("/api/v1/exchange-rate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RunOnVirtualThread
@Tag(name = "Exchange Rate", description = "Operaciones de consulta de tipo de cambio")
public class ExchangeRateController {

  private static final Logger LOG = Logger.getLogger(ExchangeRateController.class);

  @Inject
  ExchangeRateService exchangeRateService;

  /**
   * Retrieves current exchange rates for a given DNI.
   * Implements rate limiting and comprehensive error handling.
   *
   * @param dni DNI (8 digits required)
   * @return JSON response with exchange rates or error message
   */
  @GET
  @Operation(
      summary = "Consultar tipo de cambio",
      description = "Obtiene el tipo de cambio actual para un DNI valido con limite de 10 consultas diarias"
  )
  @APIResponses({
      @APIResponse(
          responseCode = "200",
          description = "Tipo de cambio obtenido exitosamente",
          content = @Content(schema = @Schema(implementation = ExchangeRateResponse.class))
      ),
      @APIResponse(responseCode = "400", description = "DNI invalido"),
      @APIResponse(responseCode = "429", description = "Limite diario de consultas excedido"),
      @APIResponse(responseCode = "503", description = "Servicio externo no disponible")
  })
  public Response getExchangeRate(
      @Parameter(description = "DNI del usuario (8 digitos)", required = true, example = "12345678")
      @QueryParam("dni")
      @NotBlank(message = "DNI es requerido")
      @Pattern(regexp = "\\d{8}", message = "DNI debe contener exactamente 8 digitos")
      String dni) {

    try {
      var response = exchangeRateService.getExchangeRate(dni);
      return Response.ok(response).build();
    } catch (WebApplicationException e) {
      return e.getResponse();
    } catch (Exception e) {
      LOG.errorf(e, "Error interno procesando DNI: %s", dni);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("""
              {"error": "Error interno del servidor", "timestamp": "%s"}
              """.formatted(java.time.Instant.now()))
          .build();
    }
  }
}