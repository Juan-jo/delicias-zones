package org.delicias.zone_featured_partners.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.delicias.common.dto.restaurant.RestaurantResumeDTO;
import org.delicias.rest.clients.RestaurantClient;
import org.delicias.zone_featured_partners.domain.model.ZoneFeaturedPartner;
import org.delicias.zone_featured_partners.domain.repository.ZoneFeaturedPartnerRepository;
import org.delicias.zone_featured_partners.dto.FeaturedPartnerItemDTO;
import org.delicias.zone_featured_partners.dto.ZoneFeaturedPartnerDTO;
import org.delicias.zones.domain.model.ZoneInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ZoneFeaturedPartnerService {

    @Inject
    ZoneFeaturedPartnerRepository repository;

    @Inject
    @RestClient
    RestaurantClient restaurantClient;

    @Transactional
    public void create(Integer zoneId, ZoneFeaturedPartnerDTO req) {

        ZoneFeaturedPartner partner = ZoneFeaturedPartner.builder()
                .sequence(req.sequence())
                .active(req.active())
                .zone(new ZoneInfo(zoneId))
                .restaurantId(req.restaurantId())
                .build();

        repository.persist(partner);

        // TODO: Add unique zoneId, restaurantId
    }

    @Transactional
    public void update(ZoneFeaturedPartnerDTO req) {

        ZoneFeaturedPartner partner = repository.findById(req.id());

        if (partner == null) {
            throw new NotFoundException("ZoneFeaturedPartner not found");
        }

        partner.setActive(req.active());
        partner.setSequence(req.sequence());

        repository.persist(partner);
    }

    @Transactional
    public void deleteById(Integer zoneId) {

        var deleted = repository.deleteById(zoneId);

        if (!deleted) {
            throw new NotFoundException("ZoneFeaturedPartner Not Found");
        }
    }

    public FeaturedPartnerItemDTO findById(Integer id) {

        ZoneFeaturedPartner partner = repository.findById(id);

        if (partner == null) {
            throw new NotFoundException("ZoneFeaturedPartner not found");
        }

        var restaurant = restaurantClient.getRestaurantsByIds(List.of(partner.getRestaurantId()))
                .stream().findAny()
                .orElseThrow(() -> new NotFoundException("Not Found Restaurant"));

        return FeaturedPartnerItemDTO.builder()
                .id(partner.getId())
                .sequence(partner.getSequence())
                .active(partner.getActive())
                .restaurant(FeaturedPartnerItemDTO.Restaurant.builder()
                        .name(restaurant.name())
                        .description(restaurant.description())
                        .logoUrl(restaurant.logoUrl())
                        .build())
                .build();
    }

    public List<FeaturedPartnerItemDTO> getByZone(Integer zoneId) {

        List<ZoneFeaturedPartner> zones = repository.findByZoneId(zoneId);

        Map<Integer, RestaurantResumeDTO> restaurantsMap = restaurantClient.getRestaurantsByIds(
                        zones.stream().map(ZoneFeaturedPartner::getRestaurantId).toList()
                )
                .stream()
                .collect(Collectors.toMap(RestaurantResumeDTO::id, p -> p));


        return zones.stream().map(it -> {

                    var restaurant = restaurantsMap.get(it.getRestaurantId());

                    if (restaurant == null) return null;

                    return FeaturedPartnerItemDTO.builder()
                            .id(it.getId())
                            .sequence(it.getSequence())
                            .active(it.getActive())
                            .restaurant(FeaturedPartnerItemDTO.Restaurant.builder()
                                    .id(restaurant.id())
                                    .name(restaurant.name())
                                    .description(restaurant.description())
                                    .logoUrl(restaurant.logoUrl())
                                    .build())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();
    }

}
