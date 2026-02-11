package org.delicias.categories.dto;

import jakarta.validation.constraints.NotNull;
import org.delicias.common.validation.OnCreate;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class CreateCategoryDTO {
        @RestForm("name")
        @NotNull(message = "The parameter is mandatory", groups = { OnCreate.class})
        public String name;

        @RestForm("sequence")
        @NotNull(message = "The parameter is mandatory", groups = { OnCreate.class})
        public Short sequence;

        @RestForm("active")
        @NotNull(message = "The parameter is mandatory", groups = { OnCreate.class})
        public Boolean active;

        @RestForm("picture")
        @NotNull(message = "The parameter is mandatory", groups = { OnCreate.class})
        public FileUpload picture;
}
