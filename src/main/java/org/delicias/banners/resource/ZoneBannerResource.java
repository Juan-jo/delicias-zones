package org.delicias.banners.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.banners.dto.ZoneBannerDTO;
import org.delicias.banners.service.ZoneBannerService;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;

@Path("/api/zones/{zoneId}/banners")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZoneBannerResource {

    @Inject
    ZoneBannerService service;

    @GET
    public Response getByZone(
            @PathParam("zoneId") Integer zoneId,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("15") int size,
            @QueryParam("orderColumn") @DefaultValue("sequence") String orderColumn,
            @QueryParam("orderDir") @DefaultValue("asc") String orderDir

    ) {

        return Response.ok(
                service.getByZone(zoneId, page, size, orderColumn, orderDir)
        ).build();
    }

    @POST
    public Response create(
            @PathParam("zoneId") Integer zoneId,
            @Valid @ConvertGroup(to = OnCreate.class) ZoneBannerDTO req) {

        service.create(zoneId, req);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(
            @Valid @ConvertGroup(to = OnUpdate.class) ZoneBannerDTO req) {

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
