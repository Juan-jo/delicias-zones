package org.delicias.mobile.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.delicias.common.dto.restaurant.RestaurantResumeDTO;
import org.delicias.common.dto.user.UserZoneDTO;
import org.delicias.featured_partners.domain.model.ZoneFeaturedPartner;
import org.delicias.featured_partners.domain.repository.ZoneFeaturedPartnerRepository;
import org.delicias.mobile.dto.MobileFeaturedPartnerDTO;
import org.delicias.rest.clients.RestaurantClient;
import org.delicias.rest.clients.UserClient;
import org.delicias.rest.security.SecurityContextService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ZoneMobileService {

    @Inject
    @RestClient
    UserClient userClient;

    @Inject
    @RestClient
    RestaurantClient restaurantClient;

    @Inject
    SecurityContextService security;

    @Inject
    ZoneFeaturedPartnerRepository repository;

    public Set<MobileFeaturedPartnerDTO> loadFeaturedPartners() {

        UserZoneDTO userZone = userClient.getUserZone(UUID.fromString(security.userId()));

        List<ZoneFeaturedPartner> partners = repository.findByZoneId(userZone.zoneId());

        Map<Integer, RestaurantResumeDTO> restaurantsMap = restaurantClient.getRestaurantsByIds(
                partners.stream().map(ZoneFeaturedPartner::getRestaurantId).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(RestaurantResumeDTO::id, p -> p));


        return partners
                .stream()
                .map(it -> {

                            RestaurantResumeDTO resume = restaurantsMap.get(it.getRestaurantId());

                            if (resume == null) return null;

                            return MobileFeaturedPartnerDTO.builder()
                                    .restaurantTmplId(it.getRestaurantId())
                                    .name(resume.name())
                                    .logoUrl(resume.logoUrl())
                                    .build();
                        }
                ).filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
