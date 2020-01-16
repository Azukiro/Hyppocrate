package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/dmp")
public class Dmp {

    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context UriInfo ui,
                             @DefaultValue("") @QueryParam("staffId") final int staffId,
                             @DefaultValue("") @QueryParam("patientId") final int patientId,
                             @DefaultValue("") @QueryParam("search") final String search,
                             @DefaultValue("") @QueryParam("paginationNumber") final int paginationNumber,
                             @DefaultValue("") @QueryParam("paginationLength") final int paginationLength) {

        return Response.ok(true).build();
    }


    // TODO: 16/01/2020
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response file(@Context UriInfo ui) {

        return Response.ok(true).build();
    }

    // TODO: 16/01/2020  
    @Path("/print/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@Context UriInfo ui) {

        return Response.ok(true).build();
    }

    // TODO: 16/01/2020  
    @Path("/search/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)   // indique que la réponse est en json
    public Response searchAll(@Context UriInfo ui) {

        return Response.ok(true).build();
    }
}
