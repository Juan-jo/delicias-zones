package org.delicias.categories.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryFilterItemDTO(

        Integer id,
        String name,
        Short sequence,
        Boolean active,
        String pictureUrl,
        List<Restaurant> restaurants
) {

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Restaurant(
            Integer id,
            String name,
            Short sequence,
            Boolean active,
            String logoUrl

    ) { }
}
