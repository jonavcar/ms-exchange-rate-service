package com.banco.abc.controller;

import com.banco.abc.exchangerate.model.TipoCambioResponse;
import com.banco.abc.exchangerate.service.ExchangeRateServicePort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * ExchangeRateController
 * <p>
 * REST controller for the exchange rate microservice, developed as part
 * of a professional exam project Quarkus.
 * <p>
 *
 * @author Joaquin Navarro Carrasco
 * @since 2025-10-03
 */

@Path("/exchange-rate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeRateController {

    private final ExchangeRateServicePort exchangeRateService;

    @Inject
    public ExchangeRateController(ExchangeRateServicePort exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GET
    public Response getTipoCambio(@QueryParam("dni") String dni) {
        try {
            TipoCambioResponse response = exchangeRateService.consultarTipoCambio(dni);
            return Response.ok(response).build();
        } catch (WebApplicationException e) {
            return e.getResponse();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error interno del servidor\"}")
                    .build();
        }
    }
}