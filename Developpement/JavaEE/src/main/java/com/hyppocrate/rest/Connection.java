package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.ArrayList;

@Path("/connection") // indique la suite du path à suivre
public class Connection {

    // Le Path pour cet api est http://localhost:8080/Hyppocrate/api/connection/
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)   // indique que la réponse est en json
    public Response test(@Context UriInfo ui) {

        return Response.ok(true).build();
    }


    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context UriInfo ui) {

        ArrayList<Object> listResult = new ArrayList<Object>();

        Integer a = Utils.callIfDeployed(Connection::example1, 1, 2);
        int b = Utils.callIfDeployed(example2(), 4);
        Integer c = (Integer) Utils.callIfDeployed(Utils.tryGet(SQLManager.getInstance().getActe(0), "name"), 5);

        listResult.add(a);
        listResult.add(b);
        listResult.add(c);
        
        return Response.ok(listResult).build();
    }

    private static Integer example1(Integer i) {
        return i;
    }
    private int example2() {
        return 3;
    }
}
