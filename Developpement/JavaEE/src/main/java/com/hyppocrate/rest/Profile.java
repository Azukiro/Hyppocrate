package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;

@Path("/profile")
public class Profile {
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response print(@Context final UriInfo ui) throws SQLException {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().printStaffype());
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }
}
