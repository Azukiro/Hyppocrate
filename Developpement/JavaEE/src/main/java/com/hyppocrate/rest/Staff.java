package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/staff/search")
public class Staff {

    // FIXME: 16/01/2020
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@Context UriInfo ui,
                        @DefaultValue("") @QueryParam("staffId") final int staffId,
                        @DefaultValue("") @QueryParam("sortColumnName") final String sortColumnName,
                        @DefaultValue("") @QueryParam("search") final String search,
                        @DefaultValue("") @QueryParam("paginationNumber") final int paginationNumber,
                        @DefaultValue("") @QueryParam("paginationLenght") final int paginationLenght) {

        //SQLManager.getInstance().
        return null;
    }

    @Path("/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortItems(@Context UriInfo ui) {

        return Response.ok(SQLManager.getInstance().patientSortItems()).build();
    }
}
