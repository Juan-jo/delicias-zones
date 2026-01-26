package org.delicias.zones.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;
import org.delicias.zones.dto.ZoneInfoDTO;
import org.delicias.zones.service.ZoneService;

@Path("/api/zones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZoneResource {

    @Inject
    ZoneService service;

    @POST
    public Response create(
            @Valid @ConvertGroup(to = OnCreate.class) ZoneInfoDTO req) {

        service.create(req);

        return Response.status(Response.Status.CREATED).build();
    }


    @PUT
    public Response update(
            @Valid @ConvertGroup(to = OnUpdate.class) ZoneInfoDTO req) {

        service.update(req);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {

        var response = service.findById(id);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {

        service.deleteById(id);
        return Response.noContent().build();
    }
}
