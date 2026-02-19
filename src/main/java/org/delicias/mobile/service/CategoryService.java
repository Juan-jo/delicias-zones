package org.delicias.mobile.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.delicias.categories.domain.repository.ZoneCategoryRepository;
import org.delicias.common.dto.user.UserZoneDTO;
import org.delicias.mobile.dto.CategoryDTO;
import org.delicias.rest.clients.UserClient;
import org.delicias.rest.security.SecurityContextService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryService {

    @Inject
    SecurityContextService security;

    @Inject
    ZoneCategoryRepository repository;

    @Inject
    @RestClient
    UserClient userClient;

    public List<CategoryDTO> loadCategories() {

        UserZoneDTO userZone = userClient.getUserZone(UUID.fromString(security.userId()));

        return repository.activesByZone(userZone.zoneId())
                .stream().map(it-> CategoryDTO.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .imageUrl(it.getImageUrl())
                        .build())
                .toList();
    }

}
