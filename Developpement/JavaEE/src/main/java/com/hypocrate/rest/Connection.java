import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.hypocrate.rest.decision.DecisionService;

@Path("/connection")
public class Connection {

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listServices(@Context UriInfo ui) {
        URI baseURI = ui.getBaseUri();

        ArrayList<GenericReference> services = new ArrayList<GenericReference>();

        services.add(new GenericReference(UriBuilder.fromUri(baseURI)
                .path(DecisionService.class).build().getRawPath()
                , "decisin"));

        return Response.ok(services).build();
    }

}
