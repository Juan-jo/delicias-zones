package org.delicias.banners.domain.model;


import jakarta.persistence.*;
import lombok.*;
import org.delicias.zones.domain.model.ZoneInfo;

@Entity
@Table(name = "zone_banners")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZoneBanner {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "zone_banner_id_seq")
    @SequenceGenerator(
            name = "zone_banner_id_seq",
            allocationSize = 1
    )
    private Integer id;

    private String title;

    private String description;

    private Short sequence;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", referencedColumnName = "id", nullable = false)
    private ZoneInfo zone;
}
