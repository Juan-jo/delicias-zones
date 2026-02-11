package org.delicias.featured_partners.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FeaturedPartnerItemDTO(
        Integer id,
        Short sequence,
        Restaurant restaurant,
        Boolean active
) {

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Restaurant(
            Integer id,
            String name,
            String description,
            String logoUrl
    ) {}
}
