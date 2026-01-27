package org.delicias.mobile.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.delicias.common.SecurityContextService;
import org.delicias.common.dto.UserZoneDTO;
import org.delicias.mobile.dto.MobileFeaturedPartnerDTO;
import org.delicias.common.rest.clients.UserClient;
import org.delicias.zone_featured_partners.domain.repository.ZoneFeaturedPartnerRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ZoneMobileService {

    @Inject
    @RestClient
    UserClient userClient;

    @Inject
    SecurityContextService security;

    @Inject
    ZoneFeaturedPartnerRepository repository;

    public Set<MobileFeaturedPartnerDTO> loadFeaturedPartners() {

        UserZoneDTO userZone = userClient.getUserZone(UUID.fromString(security.userId()));

        return repository.findByZoneId(userZone.zoneId())
                .stream()
                .map(it -> MobileFeaturedPartnerDTO.builder()
                        .zoneId(it.getZone().getId())
                        .restaurantId(it.getRestaurantId())
                        .build()).collect(Collectors.toSet());
    }

}
