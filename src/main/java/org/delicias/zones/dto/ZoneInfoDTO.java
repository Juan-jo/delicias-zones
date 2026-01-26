package org.delicias.zones.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ZoneInfoDTO(
        @NotNull(message = "ID is mandatory", groups = {OnUpdate.class})
        Integer id,

        @NotNull(message = "name is mandatory", groups = {OnCreate.class, OnUpdate.class})
        String name,

        @NotNull(message = "hasMinimumAmount is mandatory", groups = {OnCreate.class, OnUpdate.class})
        boolean hasMinimumAmount,

        BigDecimal minimumAmount,

        @NotNull(message = "active is mandatory", groups = {OnCreate.class, OnUpdate.class})
        boolean active,

        @NotEmpty
        @NotNull(message = "coordinates is mandatory", groups = {OnCreate.class, OnUpdate.class})
        List<List<Double>> coordinates,

        @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}
