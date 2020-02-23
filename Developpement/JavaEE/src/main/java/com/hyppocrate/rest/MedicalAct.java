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
import java.util.ArrayList;

@Path("/medical-act")
public class MedicalAct {


    @Path("/draft")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response draft(@Context final UriInfo ui,
                          @QueryParam("staffId") int staffId,
                          @QueryParam("patientId") int patientId,
                          @QueryParam("title") String title,
                          @QueryParam("type") int type,
                          @QueryParam("description") String description,
                          @QueryParam("file") String file) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().CreateDraftt(staffId, patientId, title, type, description, file));
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/publish")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response publish(@Context final UriInfo ui,
                          @QueryParam("staffId") int staffId,
                          @QueryParam("patientId") int patientId,
                          @QueryParam("title") String title,
                          @QueryParam("type") int type,
                          @QueryParam("description") String description,
                          @QueryParam("file") String file) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().publishActe(staffId, patientId, title, type, description, file));
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }



    @Path("/test")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response draft(@Context final UriInfo ui,
                          @QueryParam("content") String content,
                          @QueryParam("file") File file) {


        final ArrayList<Object> result = new ArrayList<>();
        result.add(content);
        result.add(file);
        return Response.ok(result).build();
    }

    
}
