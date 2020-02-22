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
    public Response delete(@Context UriInfo ui,
                             @QueryParam("draftId") final int draftId) throws SQLException {

        return Response.ok(SQLManager.getInstance().deleteDraft(draftId)).build();
    }

    // TODO: 17/01/2020
    @Path("/actions/publish")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response publish(@Context UriInfo ui,
                             @QueryParam("draftId") final int draftId,
                            @QueryParam("patientId") final int patientId,
                            @QueryParam("title") final String title,
                            @QueryParam("description") final String description,
                            @QueryParam("file") final String file) {

        //SQLManager.getInstance().pu
        try {
            return Response.ok(SQLManager.getInstance().updateEtPublierBrouillon(patientId, draftId, title, description, file)).build();
        } catch (IOException e) {
            return Responses.nullResponse();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

  

    // TODO: 17/01/2020
    @Path("/actions/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context UriInfo ui,
                             @QueryParam("draftId") final int draftId,
                           @QueryParam("patientId") final int patientId,
                           @QueryParam("title") final String title,
                           @QueryParam("description") final String description,
                           @QueryParam("file") final String file) {

        try {
            return Response.ok(SQLManager.getInstance().updateBrouillon(patientId,draftId,title,description,file)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/print/file")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printFile(@Context UriInfo ui,
                             @QueryParam("draftId") final int draftId) {

        return Responses.nullResponse();
    }
    // TODO: 17/01/2020
    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context UriInfo ui,
                             @QueryParam("patientId") final int patientId,
                             @QueryParam("paginationNumber") final int paginationNumber,
                             @QueryParam("paginationLength") final int paginationLength,
                             @QueryParam("search") final String search,
                             @QueryParam("sortItem") final String sortItem) {

        //SQLManager.getInstance().get
        //SQLManager.getInstance().updateDraft()
        try {
            return Response.ok(SQLManager.getInstance().printActe(patientId,search,sortItem,paginationNumber,paginationLength)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }

    }

    @Path("/print/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printSort(@Context UriInfo ui) {
        try {
        return Responses.objectOrCustomNull(SQLManager.getInstance().printSortActeItems());
    } catch (SQLException e) {
        return Responses.nullResponse();
    }
    }

    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context UriInfo ui,
                             @QueryParam("staffId") final int staffId,
                             @QueryParam("sortColumnName") final String sortColumnName,
                             @QueryParam("search") final String search,
                             @QueryParam("paginationNumber") final int paginationNumber,
                             @QueryParam("paginationLength") final int paginationLength) {
        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().printDraft(staffId,search,sortColumnName,paginationNumber,paginationLength));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }



}
