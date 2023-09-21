package com.github.aoxter.ThatWasGreat.Category.Data;

import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

}
