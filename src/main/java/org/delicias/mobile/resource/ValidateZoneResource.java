package org.delicias.mobile.resource;

import jakarta.inject.Inject;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.mobile.service.ValidateZoneService;

@Path("/api/zones/mobile/validate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ValidateZoneResource {


    @Inject
    ValidateZoneService service;


    @GET
    public Response validateZone(
            @QueryParam("lat") @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
            @QueryParam("lng") @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude
    ) {

        return Response.ok(
                service.validateZone(longitude, latitude)
        ).build();
    }
}
