package org.delicias.categories.resource;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.categories.dto.CategoryFilterReqDTO;
import org.delicias.categories.dto.CreateCategoryDTO;
import org.delicias.categories.dto.UpdateCategoryDTO;
import org.delicias.categories.service.ZoneCategoryService;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnFilter;
import org.delicias.common.validation.OnUpdate;

import java.io.IOException;

@Path("/api/zones/{zoneId}/category")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class ZoneCategoryResource {


    @Inject
    ZoneCategoryService service;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(
            @PathParam("zoneId") Integer zoneId,
            @Valid @ConvertGroup(to = OnCreate.class)
            @BeanParam CreateCategoryDTO req
    ) throws IOException {

        service.create(zoneId, req);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(
            @PathParam("zoneId") Integer zoneId,
            @Valid @ConvertGroup(to = OnUpdate.class)
            @BeanParam UpdateCategoryDTO req
    ) throws IOException {

        service.update(req);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{zoneCategoryId}")
    public Response getById(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("zoneCategoryId") Integer zoneCategoryId
    ) {

        return Response.ok(
                service.findById(zoneCategoryId)
        ).build();
    }


    @DELETE
    @Path("/{zoneCategoryId}")
    public Response deleteById(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("zoneCategoryId") Integer zoneCategoryId
    ) {
        service.deleteById(zoneCategoryId);
        return Response.noContent().build();
    }

    @POST
    @Path("/filter")
    public Response filter(
            @Valid @ConvertGroup(to = OnFilter.class) CategoryFilterReqDTO req
    ){

        return Response.ok(
                service.filterByZone(req)
        ).build();
    }

}
