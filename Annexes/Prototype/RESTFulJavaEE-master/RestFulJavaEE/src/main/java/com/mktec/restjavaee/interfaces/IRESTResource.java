package com.mktec.restjavaee.interfaces;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public interface IRESTResource extends Serializable {

	@GET
	@Path("auth_user/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUserByUsernameAndPassword(@PathParam("username") String username, @PathParam("password") String password);
	
}
