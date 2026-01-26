package org.delicias.zone_featured_partners.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.delicias.zone_featured_partners.dto.ZoneFeaturedPartnerDTO;
import org.delicias.zones.domain.model.ZoneInfo;

@Entity
@Table(name = "zone_featured_partners")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZoneFeaturedPartner {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "zone_featured_partners_id_seq")
    @SequenceGenerator(
            name = "zone_featured_partners_id_seq",
            allocationSize = 1
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", referencedColumnName = "id", nullable = false)
    private ZoneInfo zone;

    @Column(name = "restaurant_id")
    private Integer restaurantId;

    private Short sequence;

    private Boolean active;

}
