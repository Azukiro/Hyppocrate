package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.util.ArrayList;

@Path("/medical-act")
public class MedicalAct {


    @Path("/draft")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response draft(@Context UriInfo ui,
                          @QueryParam("staffId") final int staffId,
                          @QueryParam("nodeId") final int nodeId,
                          @QueryParam("patientId") final int patientId,
                          @QueryParam("title") final String title,
                          @QueryParam("type") final String type,
                          @QueryParam("description") final int description,
                          @QueryParam("file") final String file) {

        return Responses.objectOrCustomNull(SQLManager.getInstance().createDraft(staffId, nodeId, patientId, title, type, description, file));
    }

    // FIXME: 18/01/2020
    @Path("/prescription/draft")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response draft(@Context UriInfo ui,
                          @QueryParam("staffId") final int staffId,
                          @QueryParam("nodeId") final int nodeId,
                          @QueryParam("patientId") final int patientId,
                          @QueryParam("title") final String title,
                          @QueryParam("type") final String type,
                          @QueryParam("file") final String file) {

        return Responses.nullResponse();
    }

    @Path("/test")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response draft(@Context UriInfo ui,
                          @QueryParam("content") final String content,
                          @QueryParam("file") final File file) {


        ArrayList<Object> result = new ArrayList<>();
        result.add(content);
        result.add(file);
        return Response.ok(result).build();
    }

    
}