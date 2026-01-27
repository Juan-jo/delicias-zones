package org.delicias.mobile.resource;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.mobile.service.ZoneMobileService;

@Path("/api/zones/mobile/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZoneMobileResources {

    @Inject
    ZoneMobileService service;

    @GET
    @Authenticated
    @Path("/featured-partners")
    public Response loadFeaturedPartners() {

        var response = service.loadFeaturedPartners();
        return Response.ok(response).build();
    }

}
