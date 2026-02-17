package org.delicias.banners.dto;

import lombok.Builder;

@Builder
public record ZoneBannerItemDTO(
        Integer id,
        String title,
        Short sequence,
        Boolean active
) { }
