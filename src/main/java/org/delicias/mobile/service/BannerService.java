package org.delicias.mobile.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.delicias.banners.domain.repository.ZoneBannerRepository;
import org.delicias.common.dto.user.UserZoneDTO;
import org.delicias.mobile.dto.BannerDTO;
import org.delicias.rest.clients.UserClient;
import org.delicias.rest.security.SecurityContextService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BannerService {

    @Inject
    SecurityContextService security;

    @Inject
    ZoneBannerRepository bannerRepository;

    @Inject
    @RestClient
    UserClient userClient;


    public List<BannerDTO> loadBanners() {

        UserZoneDTO userZone = userClient.getUserZone(UUID.fromString(security.userId()));

        return bannerRepository.activesByZone(userZone.zoneId())
                .stream().map(it-> BannerDTO.builder()
                        .title(it.getTitle())
                        .description(it.getDescription())
                        .build())
                .toList();
    }
}
