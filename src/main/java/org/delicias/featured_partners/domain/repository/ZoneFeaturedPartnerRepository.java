package org.delicias.featured_partners.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.featured_partners.domain.model.ZoneFeaturedPartner;

import java.util.List;

@ApplicationScoped
public class ZoneFeaturedPartnerRepository implements PanacheRepositoryBase<ZoneFeaturedPartner, Integer> {


    public List<ZoneFeaturedPartner> findByZoneId(Integer zoneId) {
        return list("zone.id", Sort.ascending("sequence"), zoneId);
    }
}
