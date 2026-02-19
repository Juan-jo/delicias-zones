package org.delicias.mobile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FeaturedPartnerDTO(
        Integer restaurantTmplId,
        String name,
        String logoUrl,
        String address
) { }
