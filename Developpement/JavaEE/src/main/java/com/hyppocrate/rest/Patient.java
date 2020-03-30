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

        @QueryParam("sortColumnName") String sortColumnName,
        @QueryParam("search") String search,
        @QueryParam("paginationNumber") int paginationNumber,
        @QueryParam("paginationLength") int paginationLength) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().printDMP(search, sortColumnName, paginationNumber, paginationLength));
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/search/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortItems(@Context final UriInfo ui) {

        try {
            return Response.ok(SQLManager.getInstance().printSortDmpItems()).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/print/staff")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortItems(@Context final UriInfo ui,
        @QueryParam("patientId") int patientId
    ) {

        try {
            return Response.ok(SQLManager.getInstance().getPersonnalForPatient(patientId)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/affect/staff")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response affect(@Context final UriInfo ui,
        @QueryParam("nodeId") int nodeId,
        @QueryParam("staffId") int staffId,
        @QueryParam("patientId") int patientId) {

        try {
            return Response.ok(SQLManager.getInstance().affecterPatient(nodeId, staffId, patientId)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

}