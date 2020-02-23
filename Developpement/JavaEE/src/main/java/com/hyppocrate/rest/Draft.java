package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Path("/draft")
public class Draft {

    // FIXME: 14/02/2020 retirer le throws ici
    @Path("/actions/delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@Context final UriInfo ui,
                             @QueryParam("draftId") int draftId) throws SQLException {

        return Response.ok(SQLManager.getInstance().deleteDraft(draftId)).build();
    }

    // TODO: 17/01/2020
    @Path("/actions/publish")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response publish(@Context final UriInfo ui,
                             @QueryParam("draftId") int draftId,
                            @QueryParam("patientId") int patientId,
                            @QueryParam("title") String title,
                            @QueryParam("description") String description,
                            @QueryParam("file") String file) {

        //SQLManager.getInstance().pu
        try {
            return Response.ok(SQLManager.getInstance().updateEtPublierBrouillon(patientId, draftId, title, description, file)).build();
        } catch (final IOException e) {
            return Responses.errorResponse(e.toString());
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

  

    // TODO: 17/01/2020
    @Path("/actions/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context final UriInfo ui,
                             @QueryParam("draftId") int draftId,
                           @QueryParam("patientId") int patientId,
                           @QueryParam("title") String title,
                           @QueryParam("description") String description,
                           @QueryParam("file") String file) {

        try {
            return Response.ok(SQLManager.getInstance().updateBrouillon(patientId,draftId,title,description,file)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    // TODO: 22/02/2020
    @Path("/print/file")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printFile(@Context final UriInfo ui,
                             @QueryParam("draftId") int draftId) {

        return Responses.nullResponse();
    }

    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context final UriInfo ui,
                             @QueryParam("patientId") int patientId,
                             @QueryParam("paginationNumber") int paginationNumber,
                             @QueryParam("paginationLength") int paginationLength,
                             @QueryParam("search") String search,
                             @QueryParam("sortItem") String sortItem) {

        try {
            return Response.ok(SQLManager.getInstance().printActe(patientId,search,sortItem,paginationNumber,paginationLength)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }

    }

    @Path("/print/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printSort(@Context final UriInfo ui) {
        try {
        return Responses.objectOrCustomNull(SQLManager.getInstance().printSortActeItems());
    } catch (final SQLException e) {
        return Responses.errorResponse(e.toString());
    }
    }

    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context final UriInfo ui,
                             @QueryParam("staffId") int staffId,
                             @QueryParam("sortColumnName") String sortColumnName,
                             @QueryParam("search") String search,
                             @QueryParam("paginationNumber") int paginationNumber,
                             @QueryParam("paginationLength") int paginationLength) {
        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().printDraft(staffId,search,sortColumnName,paginationNumber,paginationLength));
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }



}
