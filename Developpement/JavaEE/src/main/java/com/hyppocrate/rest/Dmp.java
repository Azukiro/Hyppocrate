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
    public Response printAll(@Context UriInfo ui,
                             @QueryParam("patientId") final int patientId,
                             @QueryParam("sortColumnName") final String sortColumnName,
                             @QueryParam("search") final String search,
                             @QueryParam("paginationNumber") final int paginationNumber,
                             @QueryParam("paginationLength") final int paginationLength) {

        try {
        return Responses.objectOrError(SQLManager.getInstance().printActe(patientId, search,sortColumnName ,paginationNumber, paginationLength), "Error");
    }catch (SQLException e){
        return  Responses.nullResponse();
    }
    }

    @Path("/print/file")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@Context UriInfo ui,
                         @QueryParam("id") final int id) {
        try {
        return Responses.objectOrCustomNull(SQLManager.getInstance().getDocument(id));
        }catch (SQLException e){
            return  Responses.nullResponse();
        }
    }

    @Path("/print/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@Context UriInfo ui) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().dmpSortItems());

        }catch (SQLException e){
            return  Responses.nullResponse();
        }
    }

}
