package com.mktec.restjavaee.rest;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mktec.restjavaee.interfaces.IRESTResource;
import com.mktec.restjavaee.interfaces.IUser;
import com.mktec.restjavaee.model.User;

public class RESTResource implements IRESTResource {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private IUser dao;
	
	@Override
	public Response findUserByUsernameAndPassword(String username, String password) {
		User user = dao.findUserByUsernameAndPassword(username, password);
		if(user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(user).build();
	}
}
