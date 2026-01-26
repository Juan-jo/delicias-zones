package org.delicias.zones.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.delicias.common.dto.BaseFilterDTO;

@Setter
@Getter
@NoArgsConstructor
public class ZoneFilterReqDTO extends BaseFilterDTO {
    String zoneName;
}

