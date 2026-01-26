package org.delicias.zone_featured_partners.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.delicias.zone_featured_partners.domain.model.ZoneFeaturedPartner;
import org.delicias.zone_featured_partners.domain.repository.ZoneFeaturedPartnerRepository;
import org.delicias.zone_featured_partners.dto.ZoneFeaturedPartnerDTO;
import org.delicias.zones.domain.model.ZoneInfo;

@ApplicationScoped
public class ZoneFeaturedPartnerService {

    @Inject
    ZoneFeaturedPartnerRepository repository;

    @Transactional
    public void create(ZoneFeaturedPartnerDTO req) {

        ZoneFeaturedPartner partner = ZoneFeaturedPartner.builder()
                .sequence(req.sequence())
                .active(req.active())
                .zone(new ZoneInfo(req.zoneId()))
                .restaurantId(req.restaurantId())
                .build();

        repository.persist(partner);

        // TODO: Add unique zoneId, restaurantId
    }

    @Transactional
    public void update(ZoneFeaturedPartnerDTO req) {

        ZoneFeaturedPartner partner = repository.findById(req.id());

        if(partner == null) {
            throw new NotFoundException("ZoneFeaturedPartner not found");
        }

        partner.setActive(req.active());
        partner.setSequence(req.sequence());
        partner.setRestaurantId(req.restaurantId());
        partner.setZone(new ZoneInfo(req.zoneId()));

        repository.persist(partner);
    }

    public ZoneFeaturedPartnerDTO findById(Integer id) {
        ZoneFeaturedPartner partner = repository.findById(id);

        if(partner == null) {
            throw new NotFoundException("ZoneFeaturedPartner not found");
        }

        return ZoneFeaturedPartnerDTO.builder()
                .id(partner.getId())
                .zoneId(partner.getZone().getId())
                .sequence(partner.getSequence())
                .active(partner.getActive())
                .restaurantId(partner.getRestaurantId())
                .build();
    }

}
