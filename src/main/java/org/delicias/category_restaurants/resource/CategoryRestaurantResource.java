package org.delicias.category_restaurants.resource;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.category_restaurants.dto.AddRestaurantToCategoryDTO;
import org.delicias.category_restaurants.service.CategoryRestaurantService;
import org.delicias.common.validation.OnCreate;

import java.io.IOException;
import java.util.Map;

@Path("/api/zones/{zoneId}/category/{zoneCategoryId}/restaurant")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class CategoryRestaurantResource {

    @Inject
    CategoryRestaurantService service;


    @POST
    public Response create(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("zoneCategoryId") Integer zoneCategoryId,
            @Valid @ConvertGroup(to = OnCreate.class) AddRestaurantToCategoryDTO req
    ) throws IOException {

        service.create(zoneCategoryId, req);

        return Response.status(Response.Status.CREATED).build();
    }

    @PATCH
    @Path("/{categoryRestaurantId}")
    public Response patch(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("zoneCategoryId") Integer zoneCategoryId,
            @PathParam("categoryRestaurantId") Integer categoryRestaurantId,
            Map<String, Object> data
    ) {

        service.patch(categoryRestaurantId, data);
        return Response.ok().build();
    }


    @GET
    @Path("/{categoryRestaurantId}")
    public Response getById(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("zoneCategoryId") Integer zoneCategoryId,
            @PathParam("categoryRestaurantId") Integer categoryRestaurantId
    ) {
        return Response.ok(
                service.findById(categoryRestaurantId)
        ).build();
    }


    @DELETE
    @Path("/{categoryRestaurantId}")
    public Response deleteById(
            @PathParam("zoneId") Integer zoneId,
            @PathParam("zoneCategoryId") Integer zoneCategoryId,
            @PathParam("categoryRestaurantId") Integer categoryRestaurantId
    ) {
        service.deleteById(categoryRestaurantId);

        return Response.noContent().build();
    }

}
