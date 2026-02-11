package org.delicias.categories.dto;

import jakarta.validation.constraints.NotNull;
import org.delicias.common.validation.OnUpdate;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class UpdateCategoryDTO {

        @RestForm("id")
        @NotNull(message = "The parameter is mandatory", groups = { OnUpdate.class})
        public Integer id;

        @RestForm("name")
        @NotNull(message = "The parameter is mandatory", groups = { OnUpdate.class})
        public String name;

        @RestForm("sequence")
        @NotNull(message = "The parameter is mandatory", groups = { OnUpdate.class})
        public Short sequence;

        @RestForm("active")
        @NotNull(message = "The parameter is mandatory", groups = { OnUpdate.class})
        public Boolean active;

        @RestForm("picture")
        public FileUpload picture;
}
