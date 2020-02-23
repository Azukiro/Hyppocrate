package com.hyppocrate.rest;

import com.hyppocrate.components.AuthentificationModule;
import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;
import com.hyppocrate.utilities.Str;
import com.hyppocrate.utilities.Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/connection") // indique la suite du path à suivre
public class Connection {

    // Le Path pour cet api est http://localhost:8080/Hyppocrate/api/connection/
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)   // indique que la réponse est en json
    public Response test(@Context final UriInfo ui) {

        return Response.ok(true).build();
    }


    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context final UriInfo ui,
                          @QueryParam("login") String login,
                          @QueryParam("pwd") String pwd) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().connect(login,pwd)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
       /* if (Str.isNullOrEmpty(email) || Str.isNullOrEmpty(pwd)) return Responses.errorResponse("badConnection");

        AuthentificationModule authModule = new AuthentificationModule();


        // Récupère le membre du staff qui veut se connecter
        HashMap<String, Object> staffMember = Utils.callIfDeployed(SQLManager.getInstance().getStaffMember(email), getTestStaffMemberMap());
        String idStaffMember = (String) Utils.tryGet(staffMember, "id");

        // Essaie de le connecter
        try {
            if (!authModule.connect(idStaffMember, pwd)) {
                return Response.ok(Responses.GENERIC_NULL).build(); // connexion échouée
            }
        } catch (Exception e) {
            return Responses.errorResponse(e.toString());
        }

        int idStaffType = SQLManager.getInstance().getEnumStaffType(Integer.parseInt(idStaffMember));
        HashMap<String, Object> hashResult = new HashMap<>();
        hashResult.put("id", idStaffMember);
        hashResult.put("type", idStaffType);
        return Response.ok(hashResult).build();*/
    }

    private HashMap<String, Object> getTestStaffMemberMap() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("id", 0);
        return map;
    }

    // FIXME: 16/01/2020
    @Path("/forgot")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response forgot(@Context final UriInfo ui,
                           @QueryParam("email") String email) throws SQLException {

        /*if (Str.isNullOrEmpty(email)) return Responses.errorResponse(e.toString());
        // Récupère le membre du staff qui veut se connecter
        HashMap<String, Object> staffMember = Utils.callIfDeployed(SQLManager.getInstance().getStaffMember(email), getTestStaffMemberMap());
        Integer idStaffMember = (Integer) Utils.tryGet(staffMember, "id");
        int idStaffType = SQLManager.getInstance().getEnumStaffType(idStaffMember);

        // Génère un nouveau mot de passe, l'envoie par mail et modifie l'entrée
        String newPassword = Str.randomString(8);
        SQLManager.getInstance().updatePassword(idStaffMember, newPassword);
        // TODO: 17/01/2020 Envoie un mail ici
        
        HashMap<String, Object> hashResult = new HashMap<>();
        hashResult.put("id", idStaffMember);
        hashResult.put("type", idStaffType);
        return Response.ok(hashResult).build();*/
        return Responses.nullResponse();
    }



    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testPage(@Context final UriInfo ui,
                            @QueryParam("email") String email,
                            @HeaderParam("Connection") String co) {

        final ArrayList<Object> listResult = new ArrayList<>();

        final Integer a = Utils.callIfDeployed(Connection::example1, 1, 2);
        final int b = Utils.callIfDeployed(this.example2(), 4);
        //Integer c = (Integer) Utils.callIfDeployed(Utils.tryGet(SQLManager.getInstance().getActe(0), "name"), 5);

        listResult.add(a);
        listResult.add(b);
        //listResult.add(c);
        listResult.add(ui.getQueryParameters());
        listResult.add(ui.getPathParameters());
        listResult.add(email);
        listResult.add(co);
        return Response.ok(listResult).build();
    }
    @Path("/SQLTest")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response SQLTest(@Context final UriInfo ui) throws Exception {

        final ArrayList<Object> listResult = new ArrayList<Object>();

            final ArrayList<Object> x=new ArrayList<Object>();
            x.add("Train");
            x.add("Voiture");
            /*
            String voiture = SQLUnitTest.TestFonction(SQLUnitTest::getString, "Voiture", new TestParameters(x,null));
        String voiture2 = SQLUnitTest.TestFonction(SQLUnitTest::getString, "Pate", new TestParameters(x,null));
        x.add("histoire");
        String voiture3 = SQLUnitTest.TestFonction(SQLUnitTest::getString, "Voiture", new TestParameters(x,null));
        */
        listResult.add(SQLManager.getInstance());
            final ArrayList<Exception> exceptions=new ArrayList<Exception>();
            exceptions.add(new IllegalAccessException());
        //listResult.add(Utils.UnitTest(()-> {
           //      return SQLManager.getInstance().getString("Test","fr"); },"Test",exceptions ));


        return Response.ok(listResult).build();
    }
    private static Integer example1(final Integer i) {
        return i;
    }
    private int example2() {
        return 3;
    }
}
