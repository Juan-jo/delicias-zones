package org.delicias.mobile.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.delicias.categories.domain.repository.ZoneCategoryRepository;
import org.delicias.category_restaurants.domain.model.CategoryRestaurant;
import org.delicias.category_restaurants.domain.repository.CategoryRestaurantRepository;
import org.delicias.common.dto.PagedResult;
import org.delicias.common.dto.restaurant.RestaurantResumeDTO;
import org.delicias.common.dto.user.UserZoneDTO;
import org.delicias.minio.MinioStorageService;
import org.delicias.mobile.dto.CategoryDTO;
import org.delicias.mobile.dto.CategoryRestaurantItemDTO;
import org.delicias.rest.clients.RestaurantClient;
import org.delicias.rest.clients.UserClient;
import org.delicias.rest.security.SecurityContextService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryService {

    @Inject
    SecurityContextService security;

    @Inject
    ZoneCategoryRepository repository;

    @Inject
    CategoryRestaurantRepository categoryRestaurantRepository;

    @Inject
    @RestClient
    UserClient userClient;

    @Inject
    @RestClient
    RestaurantClient restaurantClient;

    @Inject
    MinioStorageService minioStorageService;

    public List<CategoryDTO> loadCategories() {

        UserZoneDTO userZone = userClient.getUserZone(UUID.fromString(security.userId()));

        return repository.activesByZone(userZone.zoneId())
                .stream().map(it-> CategoryDTO.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .imageUrl(minioStorageService.thumbnailUrl(it.getImageUrl()))
                        .build())
                .toList();
    }

    public PagedResult<CategoryRestaurantItemDTO> restaurantsByCategory(Integer categoryId, Integer page, Integer size) {


        List<CategoryRestaurant> categoryRestaurants = categoryRestaurantRepository.mobilePageableFilter(categoryId, page, size);

        long total = categoryRestaurantRepository.countMobilePageableFilter(categoryId);

        if (total == 0 || categoryRestaurants.isEmpty()) {
            return new PagedResult<>(
                    List.of(),
                    total,
                    page,
                    size
            );
        }

        Set<Integer> restaurantIds = categoryRestaurants.stream()
                .map(CategoryRestaurant::getRestaurantTmplId)
                .collect(Collectors.toSet());

        Map<Integer, RestaurantResumeDTO> restaurantsMap =
                restaurantClient.getRestaurantsByIds(restaurantIds)
                        .stream()
                        .collect(Collectors.toMap(RestaurantResumeDTO::id, r -> r));

        var filtered = categoryRestaurants.stream().map(it -> {

                    var restaurant = restaurantsMap.get(it.getRestaurantTmplId());

                    if (restaurant == null) return null;

                    return CategoryRestaurantItemDTO.builder()
                            .restaurantId(it.getRestaurantTmplId())
                            .name(restaurant.name())
                            .description(restaurant.description())
                            .address(restaurant.address())
                            .pictureUrl(minioStorageService.smallImage(restaurant.logoUrl()))
                            .coverUrl(minioStorageService.imgBannerUrl(restaurant.coverUrl()))
                            .build();

                })
                .filter(Objects::nonNull)
                .toList();


        return new PagedResult<>(
                filtered,
                total,
                page,
                size
        );
    }
}
