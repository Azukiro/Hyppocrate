package com.hyppocrate.rest;

import com.hyppocrate.components.AuthentificationModule;
import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    public Response login(@Context UriInfo ui,
            @DefaultValue("") @QueryParam("email") final String email,
            @DefaultValue("") @QueryParam("pwd") final String pwd) {

        // Récupère le membre du staff qui veut se connecter
        HashMap<String, Object> staffMember = Utils.callIfDeployed(SQLManager.getInstance().getStaffMember(email), getTestStaffMemberMap());
        Integer idStaffMember = (Integer) Utils.tryGet(staffMember, "id");

        // Essaie de le connecter
        if (!AuthentificationModule.connect(idStaffMember, pwd)) {
            return Response.ok(null).build(); // connexion échouée
        }

        int idStaffType = SQLManager.getInstance().getEnumStaffType(idStaffMember);
        HashMap<String, Object> hashResult = new HashMap<>();
        hashResult.put("id", idStaffMember);
        hashResult.put("type", idStaffType);
        return Response.ok(hashResult).build();
    }

    private HashMap<String, Object> getTestStaffMemberMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 0);
        return map;
    }

    // FIXME: 16/01/2020
    @Path("/forgot")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response forgot(@Context UriInfo ui,
                          @DefaultValue("") @QueryParam("email") final String email) {

        // Récupère le membre du staff qui veut se connecter
        HashMap<String, Object> staffMember = Utils.callIfDeployed(SQLManager.getInstance().getStaffMember(email), getTestStaffMemberMap());
        Integer idStaffMember = (Integer) Utils.tryGet(staffMember, "id");

        //SQLManager.getInstance().updatePassword(idStaffMember);
        return null;
    }



    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testPage(@Context UriInfo ui) {

        ArrayList<Object> listResult = new ArrayList<>();

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
