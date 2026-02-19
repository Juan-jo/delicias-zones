package org.delicias.categories.domain.model;


import jakarta.persistence.*;
import lombok.*;
import org.delicias.zones.domain.model.ZoneInfo;

@Entity
@Table(name = "zone_categories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZoneCategory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "zone_category_id_seq")
    @SequenceGenerator(
            name = "zone_category_id_seq",
            allocationSize = 1
    )
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private ZoneInfo zone;

    private Short sequence;

    private Boolean active;

    @Column(name = "image_url")
    private String imageUrl;

    public ZoneCategory(Integer id) {
        this.id = id;
    }
}

