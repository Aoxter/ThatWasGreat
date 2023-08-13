package com.github.aoxter.ThatWasGreat.service;

import com.github.aoxter.ThatWasGreat.model.Category;
import com.github.aoxter.ThatWasGreat.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return  categoryRepository.findAll();
    }

    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category add(Category category) {
        return categoryRepository.save(new Category(category.getName(), category.getDescription(),
                category.getRatingForm(), category.getFactors()));
    }

    public Optional<Category> update(Long id, Category newCategoryData) {
        Optional<Category> categoryToUpdate = categoryRepository.findById(id);
        if (categoryToUpdate.isPresent()) {
            Category categoryUpdated = categoryToUpdate.get();
            categoryUpdated.setName(newCategoryData.getName());
            categoryUpdated.setDescription(newCategoryData.getDescription());
            categoryUpdated.setRatingForm(newCategoryData.getRatingForm());
            categoryUpdated.setFactors(newCategoryData.getFactors());
            return Optional.of(categoryRepository.save(categoryUpdated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(Long id) throws Exception {
        if(id == 1 || id == 2 | id == 3 | id == 4) throw new Exception("Default categories can't be removed");
        categoryRepository.deleteById(id);
    }
}
