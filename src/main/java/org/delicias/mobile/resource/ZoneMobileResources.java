package org.delicias.mobile.resource;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.delicias.mobile.service.BannerService;
import org.delicias.mobile.service.CategoryService;
import org.delicias.mobile.service.FeaturedPartnerService;

@Authenticated
@Path("/api/zones/mobile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZoneMobileResources {

    @Inject
    FeaturedPartnerService partnerService;

    @Inject
    CategoryService categoryService;

    @Inject
    BannerService bannerService;

    @GET
    @Path("/featured-partners")
    public Response loadFeaturedPartners() {

        return Response.ok(
                partnerService.loadFeaturedPartners()
        ).build();
    }

    @GET
    @Path("/categories")
    public Response loadCategories() {

        return Response.ok(
                categoryService.loadCategories()
        ).build();
    }

    @GET
    @Path("/banners")
    public Response loadBanners() {

        return Response.ok(
                bannerService.loadBanners()
        ).build();
    }


}
