package org.delicias.categories.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.delicias.categories.domain.model.ZoneCategory;
import org.delicias.categories.domain.repository.ZoneCategoryRepository;
import org.delicias.categories.dto.*;
import org.delicias.category_restaurants.domain.model.CategoryRestaurant;
import org.delicias.category_restaurants.domain.repository.CategoryRestaurantRepository;
import org.delicias.common.dto.PagedResult;
import org.delicias.common.dto.restaurant.RestaurantResumeDTO;
import org.delicias.rest.clients.RestaurantClient;
import org.delicias.supabase.SupabaseStorageService;
import org.delicias.zones.domain.model.ZoneInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ZoneCategoryService {

    @Inject
    ZoneCategoryRepository repository;

    @Inject
    CategoryRestaurantRepository categoryRestaurantRepository;

    @Inject
    SupabaseStorageService storageService;

    @Inject
    @RestClient
    RestaurantClient restaurantClient;


    @Transactional
    public void create(Integer zoneId, CreateCategoryDTO req) throws IOException {

        String pictureUrl = storageService.uploadFile(req.picture);

        ZoneCategory category = ZoneCategory.builder()
                .name(req.name)
                .sequence(req.sequence)
                .zone(new ZoneInfo(zoneId))
                .active(req.active)
                .imageUrl(pictureUrl)
                .build();

        repository.persist(category);
    }


    @Transactional
    public void update(UpdateCategoryDTO req) throws IOException {

        ZoneCategory entity = repository.findById(req.id);

        if (entity == null) {
            throw new NotFoundException("ZoneCategory Not Found");
        }

        updatePictureIfPresent(req, entity);

        entity.setName(req.name);
        entity.setSequence(req.sequence);
        entity.setActive(req.active);
    }

    public ZoneCategoryDTO findById(Integer zoneCategoryId) {

        ZoneCategory entity = repository.findById(zoneCategoryId);

        if (entity == null) {
            throw new NotFoundException("ZoneCategory Not Found");
        }

        return ZoneCategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sequence(entity.getSequence())
                .active(entity.isActive())
                .pictureUrl(entity.getImageUrl())
                .build();
    }

    @Transactional
    public void deleteById(Integer zoneCategoryId) {


        ZoneCategory entity = repository.findById(zoneCategoryId);

        if (entity == null) {
            throw new NotFoundException("ZoneCategory Not Found");
        }

        deleteCurrentPicture(entity.getImageUrl());

        var deleted = repository.deleteById(zoneCategoryId);

        if (!deleted) {
            throw new NotFoundException("ZoneCategoryDTO Not Found");
        }
    }



    public PagedResult<CategoryFilterItemDTO> filterByZone(CategoryFilterReqDTO req) {

        List<ZoneCategory> categories = repository.filterByZone(
                req.getZoneId(),
                req.getPage(),
                req.getSize(),
                req.getOrderColumn(),
                req.toOrderDirection()
        );

        long total = repository.countByZone(req.getZoneId());

        if (total == 0 || categories.isEmpty()) {
            return new PagedResult<>(
                    List.of(),
                    total,
                    req.getPage(),
                    req.getSize()
            );
        }

        List<Integer> categoryIds = categories.stream()
                .map(ZoneCategory::getId)
                .toList();

        List<CategoryRestaurant> categoryRestaurants =
                categoryRestaurantRepository.findByZoneCategories(categoryIds);

        if (categoryRestaurants.isEmpty()) {
            List<CategoryFilterItemDTO> filtered = categories.stream()
                    .map(this::toFilterItemWithoutRestaurants)
                    .toList();

            return new PagedResult<>(filtered, total, req.getPage(), req.getSize());
        }

        List<Integer> restaurantIds = categoryRestaurants.stream()
                .map(CategoryRestaurant::getRestaurantTmplId)
                .distinct()
                .toList();

        Map<Integer, RestaurantResumeDTO> restaurantsMap =
                restaurantClient.getRestaurantsByIds(restaurantIds)
                        .stream()
                        .collect(Collectors.toMap(RestaurantResumeDTO::id, r -> r));


        Map<Integer, List<CategoryRestaurant>> relationsByCategoryId =
                categoryRestaurants.stream()
                        .collect(Collectors.groupingBy(p->p.getCategory().getId()));

        List<CategoryFilterItemDTO> filtered = categories.stream()
                .map(category -> toFilterItem(category, relationsByCategoryId, restaurantsMap))
                .toList();

        return new PagedResult<>(
                filtered,
                total,
                req.getPage(),
                req.getSize()
        );
    }


    private CategoryFilterItemDTO toFilterItemWithoutRestaurants(ZoneCategory category) {
        return CategoryFilterItemDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .sequence(category.getSequence())
                .active(category.isActive())
                .pictureUrl(category.getImageUrl())
                .restaurants(List.of())
                .build();
    }

    private CategoryFilterItemDTO toFilterItem(
            ZoneCategory category,
            Map<Integer, List<CategoryRestaurant>> relationsByCategoryId,
            Map<Integer, RestaurantResumeDTO> restaurantsMap
    ) {

        List<CategoryFilterItemDTO.Restaurant> restaurants = relationsByCategoryId
                .getOrDefault(category.getId(), List.of())
                .stream()
                .map(rel -> {

                    RestaurantResumeDTO restaurant = restaurantsMap.get(rel.getRestaurantTmplId());

                    if (restaurant == null) {
                        return null;
                    }

                    return CategoryFilterItemDTO.Restaurant.builder()
                            .id(restaurant.id())
                            .sequence(rel.getSequence()) // 🔥 aquí va la sequence de la relación
                            .name(restaurant.name())
                            .logoUrl(restaurant.logoUrl())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        return CategoryFilterItemDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .sequence(category.getSequence())
                .active(category.isActive())
                .pictureUrl(category.getImageUrl())
                .restaurants(restaurants)
                .build();
    }



    private void updatePictureIfPresent(UpdateCategoryDTO req, ZoneCategory entity) throws IOException {

        if (req.picture == null) {
            return;
        }

        String newUrl = storageService.uploadFile(req.picture);

        if (newUrl == null) {
            return;
        }

        deleteCurrentPicture(entity.getImageUrl());
        entity.setImageUrl(newUrl);
    }

    private void deleteCurrentPicture(String pictureUrl) {
        if(Optional.ofNullable(pictureUrl).isPresent()) {
            storageService.deleteFile(pictureUrl);
        }
    }
}
