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
    public Response all(@Context final UriInfo ui,
                        @DefaultValue("") @QueryParam("staffId") int staffId,
                         @QueryParam("sortColumnName") String sortColumnName,
                       @QueryParam("search") String search,
                        @DefaultValue("") @QueryParam("paginationNumber") int paginationNumber,
                        @DefaultValue("") @QueryParam("paginationLenght") int paginationLenght) {

        //SQLManager.getInstance().
        try {
              return Responses.objectOrCustomNull(SQLManager.getInstance().printDMP(search,sortColumnName,paginationNumber,paginationLenght));
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/search/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortItems(@Context final UriInfo ui) {

        try {
            return Response.ok(SQLManager.getInstance().printSortDmpItems()).build();
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/affect/staff")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response affect(@Context final UriInfo ui, @QueryParam("nodeId") int nodeId,

                           @QueryParam("staffId") int staffId,
                           @QueryParam("patientId") int patientId) {

        try {
            return Response.ok(SQLManager.getInstance().affecterPatient(nodeId,staffId,patientId)).build();
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

}
