package org.delicias.featured_partners.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.featured_partners.domain.model.ZoneFeaturedPartner;

import java.util.List;

@ApplicationScoped
public class ZoneFeaturedPartnerRepository implements PanacheRepositoryBase<ZoneFeaturedPartner, Integer> {

    private final String queryFilterByZone = "zone.id = ?1";

    public List<ZoneFeaturedPartner> findByZoneId(Integer zoneId) {
        return list("zone.id", Sort.ascending("sequence"), zoneId);
    }

    public List<ZoneFeaturedPartner> getByZone(
            Integer zoneId,
            int page,
            int size
    ) {
        return find(queryFilterByZone, Sort.by("sequence", Sort.Direction.Ascending), zoneId)
                .page(Page.of(page, size))
                .list();
    }

    public long countByZone(Integer zoneId) {
        return count(queryFilterByZone, zoneId);
    }

}
