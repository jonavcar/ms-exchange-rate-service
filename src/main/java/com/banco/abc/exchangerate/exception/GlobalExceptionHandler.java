package com.banco.abc.exchangerate.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

/**
 * GlobalExceptionHandler
 * Global exception handler for unhandled exceptions in the application.
 * Provides consistent error response format and logging.
 *
 * @author Joaquin Navarro Carrasco
 * @version 1.0.0
 * @since 2025-10-05
 */

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

  private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

  @Override
  public Response toResponse(Exception exception) {
    LOG.errorf(exception, "Error no controlado en la aplicacion");

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("""
            {
                "error": "Error interno del servidor",
                "timestamp": "%s"
            }
            """.formatted(java.time.Instant.now()))
        .build();
  }
}
