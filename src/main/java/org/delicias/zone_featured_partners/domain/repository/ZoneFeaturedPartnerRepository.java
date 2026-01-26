package org.delicias.zone_featured_partners.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.zone_featured_partners.domain.model.ZoneFeaturedPartner;

@ApplicationScoped
public class ZoneFeaturedPartnerRepository implements PanacheRepositoryBase<ZoneFeaturedPartner, Integer> {
}
