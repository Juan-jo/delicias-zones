package org.delicias.common.rest.filter;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
public class AuthorizationRequestFilter implements ClientRequestFilter {

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ClientRequestContext requestContext) {
        if (jwt != null && jwt.getRawToken() != null) {
            requestContext
                    .getHeaders()
                    .add("Authorization", "Bearer " + jwt.getRawToken());
        }
    }
}
