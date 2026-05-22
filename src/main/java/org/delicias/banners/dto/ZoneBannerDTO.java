package org.delicias.banners.dto;

import lombok.Builder;

@Builder
public record ZoneBannerDTO(
        Integer id,
        String title,
        String description,
        Short sequence,
        boolean active,
        String pictureUrl
) { }
