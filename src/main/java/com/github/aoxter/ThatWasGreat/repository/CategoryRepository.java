package com.github.aoxter.ThatWasGreat.repository;

import com.github.aoxter.ThatWasGreat.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
