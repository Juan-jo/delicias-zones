package org.delicias.mobile.dto;

import lombok.Builder;

@Builder
public record CategoryDTO(
        Integer id,
        String name,
        String imageUrl
) { }
