package org.delicias.category_restaurants.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.delicias.category_restaurants.domain.model.CategoryRestaurant;

import java.util.List;

@ApplicationScoped
public class CategoryRestaurantRepository implements PanacheRepositoryBase<CategoryRestaurant, Integer> {

    public List<CategoryRestaurant> findByZoneCategory(Integer zoneCategoryId) {
        return list("category.id", Sort.ascending("sequence"), zoneCategoryId);
    }

    public List<CategoryRestaurant> findByZoneCategories(List<Integer> zoneCategoryId) {
        return find("category.id in ?1", Sort.ascending("sequence") ,zoneCategoryId).list();
    }
}
