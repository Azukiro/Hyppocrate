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
    public Response print(@Context UriInfo ui,
                           @QueryParam("staffId") final int staffId) throws SQLException {

        try {
            return Responses.objectOrCustomNull(SQLManager.getInstance().getStaffMember(staffId));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/general")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response general(@Context UriInfo ui,
                           @QueryParam("staffId") final int staffId,
                            @QueryParam("firstName") final String firstName,
                            @QueryParam("lastName") final String lastName,
                            @QueryParam("birthdayDate") final String birthdayDate) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().modifyInfoStaff(staffId,firstName,lastName,birthdayDate)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/contact")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response contact(@Context UriInfo ui,
                            @QueryParam("staffId") final int staffId,
                            @QueryParam("phone") final String phone,
                            @QueryParam("address") final String address,
                            @QueryParam("email") final String email) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().modifyContactStaff(staffId,phone,address,email)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/pwd")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response pwd(@Context UriInfo ui,
                            @QueryParam("staffId") final int staffId,
                            @QueryParam("oldPwd") final String oldPwd,
                            @QueryParam("newPwd") final String newPwd,
                            @QueryParam("newPwdAgain") final String newPwdAgain) throws SQLException {

        try {
            return   Responses.objectOrCustomNull(SQLManager.getInstance().ResetPassword(staffId,oldPwd,newPwd,newPwdAgain));
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }


    @Path("/new/patient")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPatient(@Context UriInfo ui,
                        @QueryParam("socialNumber") final long socialNumber,
                        @QueryParam("firstName") final String firstName,
                        @QueryParam("lastName") final String lastName,
                               @QueryParam("phoneNumber") final String phoneNumber,
                               @QueryParam("email") final String email,
                               @QueryParam("address") final String address,
                        @QueryParam("birthdayDate") final String birthdayDate) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().createProfile(socialNumber,firstName,lastName,birthdayDate,address,email,phoneNumber)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo ui,
                           @QueryParam("typeId") final int typeId,
                               @QueryParam("socialNumber") final long socialNumber,
                               @QueryParam("firstName") final String firstName,
                               @QueryParam("lastName") final String lastName,
                               @QueryParam("phoneNumber") final String phoneNumber,
                               @QueryParam("email") final String email,
                               @QueryParam("address") final String address,
                               @QueryParam("birthdayDate") final String birthdayDate) throws SQLException {

        try {
            return Response.ok(SQLManager.getInstance().CreateStaff(socialNumber,firstName,lastName,birthdayDate,address,email,phoneNumber,typeId)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/infos/assignment")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignmentInfo(@Context UriInfo ui,
                          @QueryParam("staffId") final int staffId) throws SQLException {
        //A faire
        try {
            return Response.ok(SQLManager.getInstance().getStaffMember(staffId)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }

    @Path("/infos/assignment")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignmentdelete(@Context UriInfo ui,
                                   @QueryParam("staffId") final int staffId) throws SQLException {
        //A faire
        try {
            return Response.ok(SQLManager.getInstance().getStaffMember(staffId)).build();
        } catch (SQLException e) {
            return Responses.nullResponse();
        }
    }
}
