package org.delicias.banners.dto;

import jakarta.validation.constraints.NotNull;
import org.delicias.common.validation.OnCreate;
import org.delicias.common.validation.OnUpdate;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class ZoneBannerReqDTO {

        @RestForm("id")
        @NotNull(message = "ID is mandatory", groups = {OnUpdate.class})
        public Integer id;

        @RestForm("title")
        @NotNull(message = "Title is mandatory", groups = {OnCreate.class, OnUpdate.class})
        public String title;

        @RestForm("description")
        @NotNull(message = "Description is mandatory", groups = {OnCreate.class, OnUpdate.class})
        public String description;

        @RestForm("sequence")
        @NotNull(message = "Sequence is mandatory", groups = {OnCreate.class, OnUpdate.class})
        public Short sequence;

        @RestForm("active")
        @NotNull(message = "Active is mandatory", groups = {OnCreate.class, OnUpdate.class})
        public Boolean active;

        @RestForm("picture")
        @NotNull(message = "picture is mandatory", groups = {OnCreate.class})
        public FileUpload picture;
}
