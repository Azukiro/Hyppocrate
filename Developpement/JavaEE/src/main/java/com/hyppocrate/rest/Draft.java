package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
                             @QueryParam("draftId") final int draftId) {

        //SQLManager.getInstance().pu
        return Responses.nullResponse();
    }

    // TODO: 17/01/2020
    @Path("/actions/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context UriInfo ui,
                             @QueryParam("draftId") final int draftId) {

        return Responses.nullResponse();
    }

    @Path("/print/file")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printFile(@Context UriInfo ui,
                             @QueryParam("draftId") final int draftId) {

        return Responses.objectOrCustomNull(SQLManager.getInstance().getDocument(draftId));
    }
    // TODO: 17/01/2020
    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context UriInfo ui,
                             @QueryParam("draftId") final int draftId) {

        //SQLManager.getInstance().get
        //SQLManager.getInstance().updateDraft()
        return Responses.nullResponse();
    }

    @Path("/print/sort-items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printAll(@Context UriInfo ui) {

        return Responses.objectOrCustomNull(SQLManager.getInstance().draftSortItems());
    }



}
