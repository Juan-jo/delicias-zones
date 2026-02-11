package org.delicias.categories.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.delicias.common.dto.BaseFilterDTO;
import org.delicias.common.validation.OnFilter;

@Getter
public class CategoryFilterReqDTO extends BaseFilterDTO {

    @NotNull(message = "The zoneId parameter is mandatory.", groups = { OnFilter.class })
    private Integer zoneId;
}
