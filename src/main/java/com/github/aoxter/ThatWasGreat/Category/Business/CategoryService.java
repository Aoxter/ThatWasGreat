package com.github.aoxter.ThatWasGreat.Category.Business;

import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.CategoryRepository;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final EntryService entryService;

    public CategoryService(CategoryRepository categoryRepository, EntryService entryService) {
        this.categoryRepository = categoryRepository;
        this.entryService = entryService;
    }

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
            return Optional.of(categoryRepository.save(categoryUpdated));
        } else {
            return Optional.empty();
        }
    }

    public Category addFactor(Long id, String factor) throws CategoryNotFoundException, FactorAlreadyExistsException {
        Optional<Category> categoryToUpdateRaw = categoryRepository.findById(id);
        if (categoryToUpdateRaw.isPresent()) {
            Category categoryToUpdate = categoryToUpdateRaw.get();
            if(categoryToUpdate.getFactors() == null) {
                categoryToUpdate.setFactors(new HashSet<>(Arrays.asList(factor)));
            }
            if(!categoryToUpdate.getFactors().add(factor)) {
                throw new FactorAlreadyExistsException("This factor already exists in the category of the given ID");
            }
            try {
                categoryToUpdate.getEntries().forEach(entry -> entryService.addFactor(entry.getId(), factor));
            }
            catch (Exception e) {
                throw e;
            }
            return categoryRepository.save(categoryToUpdate);
        } else {
            throw new CategoryNotFoundException("Can not add new factor because category of the given ID doesn't exists.");
        }
    }

    public Category deleteFactor(Long id, String factor) throws CategoryNotFoundException, FactorAlreadyExistsException {
        Optional<Category> categoryToUpdateRaw = categoryRepository.findById(id);
        if (categoryToUpdateRaw.isPresent()) {
            Category categoryToUpdate = categoryToUpdateRaw.get();
            if(!categoryToUpdate.getFactors().contains(factor)) {
                throw new FactorNotFoundException("This factor doesn't exists in the category of the given ID");
            }
            else {
                try {
                    categoryToUpdate.getFactors().remove(factor);
                    categoryToUpdate.getEntries().forEach(entry -> entryService.deleteFactor(entry.getId(), factor));
                }
                catch(Exception e) {
                    throw e;
                }
            }
            return categoryRepository.save(categoryToUpdate);
        } else {
            throw new CategoryNotFoundException("Can not delete factor because category of the given ID doesn't exists.");
        }
    }

    public Category renameFactor(Long id, String oldFactor, String newFactor) {
        Optional<Category> categoryToUpdateRaw = categoryRepository.findById(id);
        if (categoryToUpdateRaw.isPresent()) {
            Category categoryToUpdate = categoryToUpdateRaw.get();
            if(!categoryToUpdate.getFactors().contains(oldFactor)) {
                throw new FactorNotFoundException("This factor doesn't exists in the category of the given ID");
            }
            else {
                try {
                    categoryToUpdate.getFactors().remove(oldFactor);
                    categoryToUpdate.getFactors().add(newFactor);
                    categoryToUpdate.getEntries().forEach(entry -> {
                        Byte rating = entry.getRates().get(oldFactor);
                        entryService.deleteFactor(entry.getId(), oldFactor);
                        entryService.addFactor(entry.getId(), newFactor, rating);
                    });
                }
                catch(Exception e) {
                    throw e;
                }
            }
            return categoryRepository.save(categoryToUpdate);
        }
        else {
            throw new CategoryNotFoundException("Can not rename factor because category of the given ID doesn't exists.");
        }
    }

    public void delete(Long id) throws CategoryCanNotBeRemovedException {
        if(id == 1 || id == 2 || id == 3 || id == 4) throw new CategoryCanNotBeRemovedException("Default categories can not be removed");
        categoryRepository.deleteById(id);
    }
}
