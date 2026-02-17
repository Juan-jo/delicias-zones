package org.delicias.banners.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ZoneBannerDTO(

        @NotNull(message = "ID is mandatory", groups = {OnUpdate.class})
        Integer id,

        @NotNull(message = "Title is mandatory", groups = {OnCreate.class, OnUpdate.class})
        String title,

        @NotNull(message = "Description is mandatory", groups = {OnCreate.class, OnUpdate.class})
        String description,

        @NotNull(message = "Sequence is mandatory", groups = {OnCreate.class, OnUpdate.class})
        Short sequence,

        @NotNull(message = "Active is mandatory", groups = {OnCreate.class, OnUpdate.class})
        Boolean active
) { }
