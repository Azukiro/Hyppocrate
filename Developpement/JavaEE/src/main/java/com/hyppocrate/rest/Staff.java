package com.hyppocrate.rest;

import com.hyppocrate.components.SQLManager;
import com.hyppocrate.utilities.Responses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;

@Path("/staff")
public class Staff {

    @Path("/print/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response print(@Context final UriInfo ui,
                           @QueryParam("staffId") int staffId) throws SQLException {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getStaffMember(staffId));
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/print/unit")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response printNode(@Context final UriInfo ui,
                          @QueryParam("nodeId") int nodeId) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().getStaffMemberFromNode(nodeId)).build();

        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/general")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response general(@Context final UriInfo ui,
                           @QueryParam("staffId") int staffId,
                            @QueryParam("firstName") String firstName,
                            @QueryParam("lastName") String lastName,
                            @QueryParam("birthdayDate") String birthdayDate) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().modifyInfoStaff(staffId,firstName,lastName,birthdayDate)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/contact")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response contact(@Context final UriInfo ui,
                            @QueryParam("staffId") int staffId,
                            @QueryParam("phone") String phone,
                            @QueryParam("address") String address,
                            @QueryParam("email") String email) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().modifyContactStaff(staffId,phone,address,email)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/pwd")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response pwd(@Context final UriInfo ui,
                            @QueryParam("staffId") int staffId,
                            @QueryParam("oldPwd") String oldPwd,
                            @QueryParam("newPwd") String newPwd,
                            @QueryParam("newPwdAgain") String newPwdAgain) throws SQLException {

        try {
            return   Responses.objectOrCustomNull(SQLManager.getInstance().ResetPassword(staffId,oldPwd,newPwd,newPwdAgain));
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }


    @Path("/new/patient")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPatient(@Context final UriInfo ui,
                        @QueryParam("socialNumber") long socialNumber,
                        @QueryParam("firstName") String firstName,
                        @QueryParam("lastName") String lastName,
                               @QueryParam("phoneNumber") String phoneNumber,
                               @QueryParam("email") String email,
                               @QueryParam("address") String address,
                        @QueryParam("birthdayDate") String birthdayDate) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().createProfile(socialNumber,firstName,lastName,birthdayDate,address,email,phoneNumber)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/affect")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPatient(@Context final UriInfo ui,
                               @QueryParam("staffId") int staffId,
                               @QueryParam("nodeId") int nodeId,
                               @QueryParam("isLeader") boolean isLeader) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().affecterPersonnel(staffId,nodeId,isLeader)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context final UriInfo ui,
                           @QueryParam("typeId") int typeId,
                               @QueryParam("socialNumber") long socialNumber,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName,
                               @QueryParam("phoneNumber") String phoneNumber,
                               @QueryParam("email") String email,
                               @QueryParam("address") String address,
                               @QueryParam("birthdayDate") String birthdayDate) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().CreateStaff(socialNumber,firstName,lastName,birthdayDate,address,email,phoneNumber,typeId)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/infos/assignment")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignmentInfo(@Context final UriInfo ui,
                          @QueryParam("staffId") int staffId) throws SQLException {
        //A faire
        try {
            return Response.ok(SQLManager.getInstance().getStaffMember(staffId)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/search/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchALL(@Context final UriInfo ui,
                              @QueryParam("sortColumnName") String sortColumnName,
                              @QueryParam("search") String search,
                              @QueryParam("paginationNumber") int paginationNumber,
                              @QueryParam("paginationLength") int paginationLength) throws SQLException {
        //A faire
        try {
            return Response.ok(SQLManager.getInstance().printStaff(search,sortColumnName,paginationNumber,paginationLength)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }

    @Path("/unAffect")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignmentdelete(@Context final UriInfo ui,
                                   @QueryParam("staffId") int staffId) throws SQLException {
        //A faire
        try {
            return Response.ok(SQLManager.getInstance().unAffecterPatient(staffId,-1)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }


    @Path("/assignment/delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignmentdelete2(@Context final UriInfo ui,
                                     @QueryParam("staffId") int staffId,
                                    @QueryParam("patientId") int patientId) throws SQLException {
        //A faire
        try {
            return Response.ok(SQLManager.getInstance().unAffecterPatient(staffId,patientId)).build();
        } catch (final SQLException e) {
            return Responses.errorResponse(e.toString());
        }
    }
}
