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
    public Response all(@Context UriInfo ui) {


        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllArchitecture());
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/hospital")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hospital(@Context UriInfo ui) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllHospitals());
        } catch (SQLException e) {
            return Responses.nullResponse();
        }

    }

    @Path("/pole")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response pole(@Context UriInfo ui,
                         @QueryParam("id") final int id) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllPoles(id));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/sector")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sector(@Context UriInfo ui,
                           @QueryParam("nodeId") final int nodeId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllSectors(nodeId));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/labo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response labo(@Context UriInfo ui,
                           @QueryParam("nodeId") final int nodeId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getAllLabos(nodeId));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/unit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUnit(@Context UriInfo ui,
                                @QueryParam("fatherId") final int fatherId,
                                @QueryParam("unitName") final String unitName,
                                @QueryParam("staffChiefId") final int staffChiefId) {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().createUnit(unitName, fatherId, staffChiefId));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/unit")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUnit(@Context UriInfo ui,
                               @QueryParam("nodeId") final int nodeId) {

        return Responses.objectOrCustomNull(SQLManager.getInstance().deleteUnit(nodeId));
    }

}
