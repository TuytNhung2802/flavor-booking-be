package com.flavorbooking.repositories.categories;

import com.flavorbooking.models.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Modifying
    @Transactional
    @Query(value = "update category" +
            " set title = :title where id = :id",nativeQuery = true)
    void updateCategory(@Param("id")Integer id,@Param("title")String title);
}
