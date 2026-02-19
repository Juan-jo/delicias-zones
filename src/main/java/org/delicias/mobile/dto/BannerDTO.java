package org.delicias.mobile.dto;

import lombok.Builder;

@Builder
public record BannerDTO(
        String title,
        String description
) { }