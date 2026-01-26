package org.delicias.zones.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.zones.domain.model.ZoneInfo;

@ApplicationScoped
public class ZoneRepository implements PanacheRepositoryBase<ZoneInfo, Integer> { }
