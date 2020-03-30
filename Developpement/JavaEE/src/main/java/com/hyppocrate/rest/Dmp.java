package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.HashMap;

@Path("/dmp")
public class Dmp {

    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context final UriInfo ui,
        @QueryParam("patientId") int patientId,
        @QueryParam("sortColumnName") String sortColumnName,
        @QueryParam("search") String search,
        @QueryParam("paginationNumber") int paginationNumber,
        @QueryParam("paginationLength") int paginationLength) {

        try {
            return Responses.objectOrError(SQLManager.getInstance().printActe(patientId, search, sortColumnName, paginationNumber, paginationLength), "Error");
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/print/file")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@Context final UriInfo ui,
        @QueryParam("id") int id) {
        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getDocument(id));
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/print/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@Context final UriInfo ui) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().printSortActeItems());

        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

}