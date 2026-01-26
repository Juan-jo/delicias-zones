package org.delicias.zone_featured_partners.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;
import org.delicias.zone_featured_partners.dto.ZoneFeaturedPartnerDTO;
import org.delicias.zone_featured_partners.service.ZoneFeaturedPartnerService;

@Path("/api/zones/featured-partners")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZoneFeaturedPartnerResource {

    @Inject
    ZoneFeaturedPartnerService service;

    @POST
    public Response create(
            @Valid @ConvertGroup(to = OnCreate.class) ZoneFeaturedPartnerDTO req) {

        service.create(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(
            @Valid @ConvertGroup(to = OnUpdate.class) ZoneFeaturedPartnerDTO req) {

        service.update(req);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {

        var response = service.findById(id);
        return Response.ok(response).build();
    }
}
