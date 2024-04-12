package org.acme;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMappers {

    @ServerExceptionMapper
    public Response handleExceptions(WebApplicationException exception) {
        return Response.fromResponse(exception.getResponse()).entity("ExceptionMappers was here").build();
    }
}
