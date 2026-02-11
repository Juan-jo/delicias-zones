package org.delicias.category_restaurants.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryRestaurantDTO(
        Integer id,
        Short sequence,
        Boolean active,
        Restaurant restaurant
) {

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Restaurant(
            Integer id,
            String name,
            String logoUrl
    ) {}
}
