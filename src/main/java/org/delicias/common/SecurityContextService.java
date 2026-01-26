package org.delicias.common;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class SecurityContextService {

    @Inject
    JsonWebToken jwt;

    public String userId() {
        return jwt.getSubject();
    }

    public String email() {
        return jwt.getClaim("email");
    }
}
