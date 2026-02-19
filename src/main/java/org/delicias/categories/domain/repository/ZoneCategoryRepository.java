package org.delicias.categories.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.categories.domain.model.ZoneCategory;

import java.util.List;

@ApplicationScoped
public class ZoneCategoryRepository implements PanacheRepositoryBase<ZoneCategory, Integer> {

    private final String queryFilter = "zone.id = ?1";


    public List<ZoneCategory> filterByZone(
            Integer zoneId,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction
    ) {

        return find(queryFilter, Sort.by(sortBy, direction), zoneId)
                .page(Page.of(page, size))
                .list();
    }

    public List<ZoneCategory> activesByZone(
            Integer zoneId
    ) {

        return find(
                "zone.id = ?1 and active = ?2",
                Sort.by("sequence", Sort.Direction.Ascending),
                zoneId, true
        ).list();
    }


    public long countByZone(Integer zoneId) {
        return count(queryFilter, zoneId);
    }

}
