package org.delicias.featured_partners.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;

@Builder
public record ZoneFeaturedPartnerDTO(
        @NotNull(message = "ID type is mandatory", groups = {OnUpdate.class})
        Integer id,

        @NotNull(message = "restaurantId type is mandatory", groups = {OnCreate.class})
        Integer restaurantId,

        @NotNull(message = "sequence type is mandatory", groups = {OnCreate.class,OnUpdate.class})
        Short sequence,

        @NotNull(message = "active type is mandatory", groups = {OnCreate.class,OnUpdate.class})
        Boolean active
) { }
