package com.flavorbooking.services.dish;

import com.flavorbooking.models.Dish;
import com.flavorbooking.repositories.Restaurant.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService{
    @Autowired
    DishRepository dishRepository;

    @Override
    public Page<Dish> getAllDishes(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer id, String description, Double price, Boolean featured, Integer categoryId, String title) {
        try {
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Dish> dishPage = dishRepository.getAllDish(id, description, price, featured, categoryId, title, pageable);
                return dishPage;
            } else {
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Dish> dishPage = dishRepository.getAllDish(id, description, price, featured, categoryId, title, pageable);
                return dishPage;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Page.empty();
        }
    }
}
