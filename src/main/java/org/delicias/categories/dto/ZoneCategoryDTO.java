package org.delicias.categories.dto;

import lombok.Builder;

@Builder
public record ZoneCategoryDTO(
        Integer id,
        String name,
        Short sequence,
        Boolean active,
        String pictureUrl
) { }

