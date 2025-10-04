package com.banco.abc.controller;

import com.banco.abc.controller.mapper.ExchangeRateMapper;
import com.banco.abc.domain.service.ExchangeRateService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/exchange-rate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateMapper mapper;

    @Inject
    public ExchangeRateController(ExchangeRateService exchangeRateService, ExchangeRateMapper mapper) {
        this.exchangeRateService = exchangeRateService;
        this.mapper = mapper;
    }

    @GET
    public Response getTipoCambio(@QueryParam("dni") String dni) {
        try {
            var exchangeRate = exchangeRateService.getExchangeRate(dni);
            var responseDto = mapper.toDto(exchangeRate);
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