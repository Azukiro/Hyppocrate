package com.hypocrate.rest;

import com.hypocrate.rest.decision.DecisionService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;

@Path("/")
public class EntryPointService {

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listServices(@Context UriInfo ui) {
        URI baseURI = ui.getBaseUri();

        ArrayList<GenericReference> services = new ArrayList<GenericReference>();

        services.add(new GenericReference(UriBuilder.fromUri(baseURI)
                .path(DecisionService.class).build().getRawPath()
                , "decisin"));

        return Response.ok(services).build();
    }

}
