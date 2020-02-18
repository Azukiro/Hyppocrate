package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;

@Path("/patient/search")
public class Patient {

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
        try {
              return Responses.objectOrCustomNull(SQLManager.getInstance().printDMP(search,sortColumnName,paginationNumber,paginationLenght));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortItems(@Context UriInfo ui) {

        return Response.ok(SQLManager.getInstance().printSortDmpItems()).build();
    }

}
