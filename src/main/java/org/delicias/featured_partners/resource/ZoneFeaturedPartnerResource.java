package org.delicias.featured_partners.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;
import org.delicias.featured_partners.dto.ZoneFeaturedPartnerDTO;
import org.delicias.featured_partners.service.ZoneFeaturedPartnerService;

@Path("/api/zones/{zoneId}/featured-partners")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZoneFeaturedPartnerResource {

    @Inject
    ZoneFeaturedPartnerService service;

    @GET
    public Response getByZone(
            @PathParam("zoneId") Integer zoneId
    ) {

        return Response.ok(
                service.getByZone(zoneId)
        ).build();
    }

    @POST
    public Response create(
            @PathParam("zoneId") Integer zoneId,
            @Valid @ConvertGroup(to = OnCreate.class) ZoneFeaturedPartnerDTO req) {

        service.create(zoneId, req);
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
    public Response findById(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("id") Integer id) {

        var response = service.findById(id);
        return Response.ok(response).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteById(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("id") Integer id) {

        service.deleteById(id);

        return Response.noContent().build();
    }





}
