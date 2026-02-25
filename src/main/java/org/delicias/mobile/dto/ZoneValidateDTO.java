package org.delicias.mobile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ZoneValidateDTO(
        boolean isInside,
        ZoneInfo zoneInfo
) {
    @Builder
    public static record ZoneInfo(
            Integer zoneId,
            String name,
            boolean active
    ){}
}
