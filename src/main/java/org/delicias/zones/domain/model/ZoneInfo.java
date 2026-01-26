package org.delicias.zones.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.delicias.zones.dto.ZoneInfoDTO;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "zone_info")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZoneInfo {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "zone_id_seq")
    @SequenceGenerator(
            name = "zone_id_seq",
            allocationSize = 1
    )
    private Integer id;

    private String name;

    @Column(name = "has_minimum_amount")
    private boolean hasMinimumAmount;


    @Column(name = "minimum_amount", precision = 5, scale = 2)
    private BigDecimal minimumAmount;

    private boolean active;

    @Column(columnDefinition = "geometry(POLYGON,4326)")
    private Polygon area;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;


    public ZoneInfo(Integer id) {
        this.id = id;
    }

    public void update(ZoneInfoDTO req, Polygon area) {

        name = req.name();
        hasMinimumAmount = req.hasMinimumAmount();
        minimumAmount = req.minimumAmount();
        active = req.active();
        this.area = area;
    }
}
