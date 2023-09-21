package com.github.aoxter.ThatWasGreat.Category.Business;

import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.CategoryRepository;
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

    public Optional<Category> getByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name));
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
            //Blocked because of potential problems with Entries synchronization
            //categoryUpdated.setFactors(newCategoryData.getFactors());
            return Optional.of(categoryRepository.save(categoryUpdated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(Long id) throws CategoryCanNotBeRemoved {
        if(id == 1 || id == 2 | id == 3 | id == 4) throw new CategoryCanNotBeRemoved("Default categories can not be removed");
        categoryRepository.deleteById(id);
    }
}
