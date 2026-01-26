package org.delicias.zones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ZoneItemDTO(
        Integer id,
        String name,
        boolean active
) { }
