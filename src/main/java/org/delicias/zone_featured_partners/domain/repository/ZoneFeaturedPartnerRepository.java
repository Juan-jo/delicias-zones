package org.delicias.zone_featured_partners.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.zone_featured_partners.domain.model.ZoneFeaturedPartner;
import org.delicias.zones.domain.model.ZoneInfo;

import java.util.List;

@ApplicationScoped
public class ZoneFeaturedPartnerRepository implements PanacheRepositoryBase<ZoneFeaturedPartner, Integer> {


    public List<ZoneFeaturedPartner> findByZoneId(Integer zoneId) {
        return list("zone.id", zoneId);
    }
}
