package com.hyppocrate.rest.examples;

import javax.ejb.Stateless;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Path("decision")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class DecisionService {
    @PersistenceContext
    EntityManager em;

    @Path("/")
    @GET
    public Response listPurchases(@Context UriInfo ui,
                                  @DefaultValue("") @QueryParam("email") String email) {
        return Response.ok().
                entity(this.queryUser(email))
                .build();
    }

    @Path("/")
    @POST
    public Response purchase(@Context UriInfo uriInfo, Purchase purchase) {
        if (purchase.getAmount() > 1000)
            return this.generateResponse(false, "amount");

        final User user = this.em.find(User.class, purchase.getEmail());
        if (user == null) {
            if (!this.isEmailValid(purchase.getEmail()))
                return Response.status(400).entity("Malformed email").build();

            this.em.persist(new User(purchase.getEmail(), purchase.getAmount()));
            return this.generateResponse(true, "ok");
        }

        if (user.getAmount() + purchase.getAmount() > 1000)
            return this.generateResponse(false, "debt");

        user.increaseAmount(purchase.getAmount());

        return this.generateResponse(true, "ok");
    }

    private boolean isEmailValid(final String email) {
        boolean result = true;
        try {
            final InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (final AddressException ex) {
            result = false;
        }
        return result;
    }

    private Response generateResponse(boolean accepted, String reason) {
        return Response.ok().entity(new PurchaseResponse(accepted, reason)).build();
    }
    @SuppressWarnings("unchecked")
    private List<User> queryUser(final String email) {
        final List<User> users;
        if (email.isEmpty())
            users = this.em.createQuery("SELECT m FROM User m").getResultList();
        else {
            final Query query = this.em.createQuery("SELECT m FROM User m WHERE m.email=:email");
            query.setParameter("email", email);
            users = query.getResultList();
        }
        return users;
    }
}
