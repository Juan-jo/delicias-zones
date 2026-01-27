package org.delicias.common.rest.clients;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.delicias.common.dto.UserZoneDTO;

import org.delicias.common.rest.filter.AuthorizationRequestFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/api/users")
@RegisterRestClient(configKey = "users-service")
@RegisterProvider(AuthorizationRequestFilter.class)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserClient {

    @GET
    @Path("/{id}/zone")
    UserZoneDTO getUserZone(@PathParam("id") UUID userId);

}
