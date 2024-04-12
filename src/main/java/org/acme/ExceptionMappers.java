package org.acme;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMappers {

    @Context
    private UriInfo uriInfo;
    
    @ServerExceptionMapper
    public Response handleExceptions(WebApplicationException exception) {
        return Response.fromResponse(exception.getResponse()).entity("ExceptionMappers was here").build();
    }
    
    @ServerExceptionMapper
    public Response handleWeb404(NotFoundException exception) {
        String path = uriInfo.getRequestUri().getPath();
        if(path.startsWith("/api")){
            return handleExceptions(exception);
        }
        return Response.seeOther(URI.create("/")).build();
    }
    
}
