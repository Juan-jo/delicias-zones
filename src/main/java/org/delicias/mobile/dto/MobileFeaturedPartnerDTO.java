package org.delicias.mobile.dto;

import lombok.Builder;

@Builder
public record MobileFeaturedPartnerDTO(
        Integer restaurantId,
        Integer zoneId
) { }
