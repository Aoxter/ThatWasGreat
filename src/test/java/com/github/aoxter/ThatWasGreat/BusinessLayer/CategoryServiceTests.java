package com.github.aoxter.ThatWasGreat.BusinessLayer;

import com.github.aoxter.ThatWasGreat.Category.Business.*;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.CategoryRepository;
import com.github.aoxter.ThatWasGreat.Category.Data.RatingForm;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.*;


public class CategoryServiceTests {
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private EntryService entryService;

    @BeforeEach
    void setupService() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        entryService = Mockito.mock(EntryService.class);
        categoryService = new CategoryService(categoryRepository, entryService);
    }

    @Test
    void updateCategoryCorrect() {
        Category categoryOld = new Category("Old Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Category categoryNew = new Category("New Category", "Lorem ipsum", RatingForm.STARS, new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryOld));
        Mockito.when(categoryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Optional<Category> categoryUpdated = categoryService.update(1L, categoryNew);
        Assertions.assertEquals(true, categoryUpdated.isPresent());
        Assertions.assertEquals(categoryNew.getName(), categoryUpdated.get().getName());
        Assertions.assertEquals(categoryNew.getDescription(), categoryUpdated.get().getDescription());
        Assertions.assertEquals(categoryNew.getRatingForm(), categoryUpdated.get().getRatingForm());
        Assertions.assertEquals(categoryNew.getFactors(), categoryUpdated.get().getFactors());
    }

    @Test
    void deleteCategoryFailedBecauseCategoryIsDefault() {
        Assertions.assertThrows(CategoryCanNotBeRemovedException.class, () -> {
            categoryService.delete(2L);
        });
    }

    @Test
    void addFactorFailedBecauseFactorAlreadyExists() {
        Category category = new Category("Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Assertions.assertThrows(FactorAlreadyExistsException.class, () -> {
            categoryService.addFactor(1L,"Factor B");
        });
    }

    @Test
    void addFactorFailedBecauseCategoryNotFound() {
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.addFactor(1L,"Factor B");
        });
    }

    @Test
    void addFactorCategoryUpdatedCorrectly() {
        Category category = new Category("Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Category categoryUpdated = categoryService.addFactor(1L,"Factor C");
        Set<String> factors = Set.of("Factor A", "Factor B", "Factor C");
        Assertions.assertEquals(factors, categoryUpdated.getFactors());
    }

    @Test
    void deleteFactorFailedBecauseCategoryNotFound() {
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteFactor(1L,"Factor B");
        });
    }

    @Test
    void deleteFactorFailedBecauseFactorNotFound() {
        Category category = new Category("Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Assertions.assertThrows(FactorNotFoundException.class, () -> {
            categoryService.deleteFactor(1L,"Factor C");
        });
    }

    @Test
    void deleteFactorCategoryUpdatedCorrectly() {
        Category category = new Category("Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Category categoryUpdated = categoryService.deleteFactor(1L,"Factor B");
        Set<String> factors = Set.of("Factor A");
        Assertions.assertEquals(factors, categoryUpdated.getFactors());
    }

    @Test
    void renameFactorFailedBecauseCategoryNotFound() {
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.renameFactor(1L,"Factor B", "FactorC");
        });
    }

    @Test
    void renameFactorFailedBecauseFactorNotFound() {
        Category category = new Category("Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Assertions.assertThrows(FactorNotFoundException.class, () -> {
            categoryService.renameFactor(1L,"Factor C", "Factor B");
        });
    }

    @Test
    void renameFactorCategoryUpdatedCorrectly() {
        Category category = new Category("Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Mockito.when(categoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Category categoryUpdated = categoryService.renameFactor(1L,"Factor B", "Factor C");
        Set<String> factors = Set.of("Factor A", "Factor C");
        Assertions.assertEquals(factors, categoryUpdated.getFactors());
    }
}
