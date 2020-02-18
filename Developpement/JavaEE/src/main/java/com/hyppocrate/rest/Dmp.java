package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;

@Path("/dmp")
public class Dmp {

    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context UriInfo ui,
                             @QueryParam("staffId") final int staffId,
                             @QueryParam("patientId") final int patientId,
                             @QueryParam("actPrintableName") final String actPrintableName,
                             @QueryParam("search") final String search,
                             @QueryParam("paginationNumber") final int paginationNumber,
                             @QueryParam("paginationLength") final int paginationLength) {

        
        return Responses.objectOrError(SQLManager.getInstance().searchDMPs(staffId, patientId, actPrintableName, search,paginationNumber, paginationLength), "Error");
    }

    @Path("/print/file")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@Context UriInfo ui,
                         @QueryParam("id") final int id) {

        return Responses.objectOrCustomNull(SQLManager.getInstance().getDocument(id));
    }

    @Path("/print/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(@Context UriInfo ui) {

        return Responses.objectOrCustomNull(SQLManager.getInstance().dmpSortItems());
    }

}
