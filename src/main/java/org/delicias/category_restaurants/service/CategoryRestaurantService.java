package org.delicias.category_restaurants.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.delicias.categories.domain.model.ZoneCategory;
import org.delicias.category_restaurants.domain.model.CategoryRestaurant;
import org.delicias.category_restaurants.domain.repository.CategoryRestaurantRepository;
import org.delicias.category_restaurants.dto.AddRestaurantToCategoryDTO;
import org.delicias.category_restaurants.dto.CategoryRestaurantDTO;
import org.delicias.rest.clients.RestaurantClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CategoryRestaurantService {

    @Inject
    CategoryRestaurantRepository repository;

    @Inject
    @RestClient
    RestaurantClient restaurantClient;

    @Transactional
    public void create(Integer zoneCategoryId, AddRestaurantToCategoryDTO req) {

        CategoryRestaurant entity = CategoryRestaurant.builder()
                .restaurantTmplId(req.restaurantTmplId())
                .category(new ZoneCategory(zoneCategoryId))
                .sequence(req.sequence())
                .active(req.active())
                .build();

        repository.persist(entity);
    }

    public CategoryRestaurantDTO findById(Integer categoryRestaurantId) {

        var entity = repository.findById(categoryRestaurantId);

        if (entity == null) {
            throw new NotFoundException("CategoryRestaurant Not Found");
        }

        var restaurant = restaurantClient.getRestaurantsByIds(
                        List.of(entity.getRestaurantTmplId())
                )
                .stream().findAny()
                .orElseThrow(() -> new NotFoundException("RestaurantResume Not Found"));

        return CategoryRestaurantDTO.builder()
                .id(entity.getId())
                .sequence(entity.getSequence())
                .active(entity.getActive())
                .restaurant(CategoryRestaurantDTO.Restaurant.builder()
                        .id(restaurant.id())
                        .name(restaurant.name())
                        .logoUrl(restaurant.logoUrl())
                        .build())
                .build();
    }

    @Transactional
    public void patch(Integer categoryRestaurantId, Map<String, Object> data) {

        var entity = repository.findById(categoryRestaurantId);

        if (entity == null) {
            throw new NotFoundException("CategoryRestaurant Not Found");
        }

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CategoryRestaurant patched = mapper.convertValue(data, CategoryRestaurant.class);

        if (patched.getSequence() != null) {
            entity.setSequence(patched.getSequence());
        }

        if (patched.getActive() != null) {
            entity.setActive(patched.getActive());
        }
    }


    @Transactional
    public void deleteById(Integer id) {
        var deleted = repository.deleteById(id);

        if (!deleted) {
            throw new NotFoundException("CategoryRestaurant Not Found");
        }
    }
}
