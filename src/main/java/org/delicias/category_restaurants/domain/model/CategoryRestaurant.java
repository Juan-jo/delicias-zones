package org.delicias.category_restaurants.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.delicias.categories.domain.model.ZoneCategory;

@Entity
@Table(name = "zone_category_restuarants")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRestaurant {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "zone_category_restarant_id_seq")
    @SequenceGenerator(
            name = "zone_category_restarant_id_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(name = "restaurant_tmpl_id")
    private Integer restaurantTmplId;

    @ManyToOne
    @JoinColumn(name = "zone_category_id", referencedColumnName = "id")
    private ZoneCategory category;


    private Short sequence;

    private Boolean active;
}
