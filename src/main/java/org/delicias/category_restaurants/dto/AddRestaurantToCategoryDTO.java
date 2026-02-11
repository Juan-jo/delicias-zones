package org.delicias.category_restaurants.dto;

import jakarta.validation.constraints.NotNull;
import org.delicias.common.validation.OnCreate;

public record AddRestaurantToCategoryDTO(
        @NotNull(message = "The parameter is mandatory", groups = { OnCreate.class})
        Integer restaurantTmplId,

        @NotNull(message = "The parameter is mandatory", groups = { OnCreate.class})
        Short sequence,

        @NotNull(message = "The parameter is mandatory", groups = { OnCreate.class})
        Boolean active
) { }
