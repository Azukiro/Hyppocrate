package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;

@Path("/patient")
public class Patient {

    // FIXME: 16/01/2020
    @Path("/search/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@Context UriInfo ui,
                        @DefaultValue("") @QueryParam("staffId") final int staffId,
                         @QueryParam("sortColumnName") final String sortColumnName,
                       @QueryParam("search") final String search,
                        @DefaultValue("") @QueryParam("paginationNumber") final int paginationNumber,
                        @DefaultValue("") @QueryParam("paginationLenght") final int paginationLenght) {

        //SQLManager.getInstance().
        try {
              return Responses.objectOrCustomNull(SQLManager.getInstance().printDMP(search,sortColumnName,paginationNumber,paginationLenght));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/search/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortItems(@Context UriInfo ui) {

        try {
            return Response.ok(SQLManager.getInstance().printSortDmpItems()).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/affect/staff")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response affect(@Context UriInfo ui,  @QueryParam("nodeId") final int nodeId,

                          @QueryParam("staffId") final int staffId,
                          @QueryParam("patientId") final int patientId) {

        try {
            return Response.ok(SQLManager.getInstance().affecterPatient(nodeId,staffId,patientId)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

}
