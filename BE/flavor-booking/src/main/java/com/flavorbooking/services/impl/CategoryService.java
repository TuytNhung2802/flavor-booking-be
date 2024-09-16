package com.flavorbooking.services.impl;

import com.flavorbooking.models.Category;
import com.flavorbooking.repositories.categories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category findCateById(Integer id){
        return  categoryRepository.findById(id).orElse(null);
    }
    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void deleteCategory(Integer id){
        categoryRepository.deleteById(id);
    }

    public  void  updateCategory(Category category){
        categoryRepository.updateCategory(category.getId(),category.getTitle());
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

}
