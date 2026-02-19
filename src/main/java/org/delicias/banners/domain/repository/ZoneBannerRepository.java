package org.delicias.banners.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.banners.domain.model.ZoneBanner;
import org.delicias.categories.domain.model.ZoneCategory;

import java.util.List;

@ApplicationScoped
public class ZoneBannerRepository implements PanacheRepositoryBase<ZoneBanner, Integer> {

    private final String queryFilterByZone = "zone.id = ?1";


    public List<ZoneBanner> getByZone(
            Integer zoneId,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction
    ) {

        return find(queryFilterByZone, Sort.by(sortBy, direction), zoneId)
                .page(Page.of(page, size))
                .list();
    }

    public List<ZoneBanner> activesByZone(
            Integer zoneId
    ) {

        return find(
                "zone.id = ?1 and active = ?2",
                Sort.by("sequence", Sort.Direction.Ascending),
                zoneId, true
        ).list();
    }

    public long countByZone(Integer zoneId) {
        return count(queryFilterByZone, zoneId);
    }
}
