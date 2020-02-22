package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;

@Path("/infrastructure")
public class Infrastructure {

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@Context final UriInfo ui) {


        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllArchitecture());
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@Context final UriInfo ui, @QueryParam("nodeId") int nodeId
                        ) {


        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllArchitecture());
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/hospital")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hospital(@Context final UriInfo ui) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllHospital());
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }

    }

    @Path("/pole")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response pole(@Context final UriInfo ui,
                         @QueryParam("nodeId") int id) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllPole(id));
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/sector")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sector(@Context final UriInfo ui,
                           @QueryParam("nodeId") int nodeId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllSector(nodeId));
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/labo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response labo(@Context final UriInfo ui,
                           @QueryParam("nodeId") int nodeId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllLabo(nodeId));
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/unit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUnit(@Context final UriInfo ui,
                                @QueryParam("fatherId") int fatherId,
                                @QueryParam("name") String name,
                                @QueryParam("staffLeaderId") int staffLeaderId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().createUnit(name, fatherId, staffLeaderId));
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }
    @Path("/unit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSectorLabo(@Context final UriInfo ui,
                               @QueryParam("fatherId") int fatherId,
                               @QueryParam("name") String name,
                                     @QueryParam("isLaboratory") boolean isLaboratory,
                               @QueryParam("staffLeaderId") int staffLeaderId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().createSector(name,fatherId,staffLeaderId,isLaboratory));
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }
    @Path("/unit")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUnit(@Context final UriInfo ui,
                               @QueryParam("nodeId") int nodeId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().deleteUnit(nodeId));
        } catch (final SQLException e) {
            return Responses.nullResponse();
        }
    }

}
