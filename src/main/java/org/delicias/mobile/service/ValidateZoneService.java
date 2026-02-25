package org.delicias.mobile.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.delicias.mobile.dto.ZoneValidateDTO;
import org.delicias.zones.domain.model.ZoneInfo;
import org.delicias.zones.domain.repository.ZoneRepository;


@ApplicationScoped
public class ValidateZoneService {

    @Inject
    ZoneRepository zoneRepository;


    public ZoneValidateDTO validateZone(Double longitude, Double latitude) {

        return zoneRepository.findZoneByCoordinates(longitude, latitude)
                .map(zone -> ZoneValidateDTO.builder()
                        .isInside(true)
                        .zoneInfo(ZoneValidateDTO.ZoneInfo.builder()
                                .zoneId(zone.getId())
                                .name(zone.getName())
                                .active(zone.isActive())
                                .build())
                        .build())
                .orElseGet(() -> ZoneValidateDTO.builder()
                        .isInside(false)
                        .build());
    }
}
