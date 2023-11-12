package com.github.aoxter.ThatWasGreat.BusinessLayer;

import com.github.aoxter.ThatWasGreat.Category.Business.CategoryCanNotBeRemovedException;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryService;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.CategoryRepository;
import com.github.aoxter.ThatWasGreat.Category.Data.RatingForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;


public class CategoryServiceTests {
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setupService() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void updateCategoryCorrect() {
        Category categoryOld = new Category("Old Category", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor A", "Factor B"));
        Category categoryNew = new Category("New Category", "Lorem ipsum", RatingForm.STARS, Arrays.asList("Factor A", "Factor B"));
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryOld));
        Mockito.when(categoryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Optional<Category> categoryUpdated = categoryService.update(1L, categoryNew);
        Assertions.assertEquals(categoryUpdated.isPresent(), true);
        Assertions.assertEquals(categoryUpdated.get().getName(), categoryNew.getName());
        Assertions.assertEquals(categoryUpdated.get().getDescription(), categoryNew.getDescription());
        Assertions.assertEquals(categoryUpdated.get().getRatingForm(), categoryNew.getRatingForm());
        Assertions.assertEquals(categoryUpdated.get().getFactors(), categoryNew.getFactors());
    }

    @Test
    void deleteCategoryFailedBecauseCategoryIsDefault() {
        Assertions.assertThrows(CategoryCanNotBeRemovedException.class, () -> {
            categoryService.delete(2L);
        });
    }
}
