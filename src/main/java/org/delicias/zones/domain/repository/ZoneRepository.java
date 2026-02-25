package org.delicias.zones.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.zones.domain.model.ZoneInfo;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ZoneRepository implements PanacheRepositoryBase<ZoneInfo, Integer> {

    private final String queryFilter = "LOWER(name) LIKE LOWER(?1)";

    public List<ZoneInfo> searchByName(
            String name,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction
    ) {

        PanacheQuery<ZoneInfo> query;

        if (name == null || name.isBlank()) {
            query = findAll(
                    Sort.by(sortBy, direction)
            );
        } else {
            query = find(
                    queryFilter,
                    Sort.by(sortBy, direction),
                    "%" + name + "%"
            );
        }

        return query
                .page(Page.of(page, size))
                .list();

    }

    public long countByName(String name) {
        if (name == null || name.isBlank()) {
            return count();
        }
        return count(
                queryFilter,
                "%" + name + "%"
        );
    }

    public Optional<ZoneInfo> findZoneByCoordinates(double longitude, double latitude) {
        // Usamos la función nativa ST_Contains de PostGIS
        // 'SRID 4326' coincide con tu definición de la columna geometry
        return find("ST_Contains(area, ST_SetSRID(ST_Point(?1, ?2), 4326)) = true",
                longitude, latitude).firstResultOptional();
    }

}
