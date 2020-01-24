package com.hyppocrate.rest;

import com.hyppocrate.components.LangManager;
import com.hyppocrate.utilities.ERRORS;
import com.hyppocrate.utilities.Responses;
import com.hyppocrate.utilities.Str;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

// TODO: 18/01/2020
@Path("/langue")
public class Langue {

    @Path("/get-string")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getString(@Context UriInfo ui,
                             @QueryParam("langue") final String idString,
                             @HeaderParam("langue") final String langueHeader) {

        if (Str.isNullOrEmpty(idString)) return Responses.errorResponse(ERRORS.ARGS_NULL);

        LangManager lm = new LangManager();

        return Response.ok(lm.getString(idString)).build();
    }}
