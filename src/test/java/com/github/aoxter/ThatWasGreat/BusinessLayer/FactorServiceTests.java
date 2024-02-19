package com.github.aoxter.ThatWasGreat.BusinessLayer;

import com.github.aoxter.ThatWasGreat.Category.Business.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryService;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.CategoryRepository;
import com.github.aoxter.ThatWasGreat.Category.Data.RatingForm;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryNotFoundException;
import com.github.aoxter.ThatWasGreat.Entry.Data.Entry;
import com.github.aoxter.ThatWasGreat.Entry.Data.EntryRepository;
import com.github.aoxter.ThatWasGreat.Factor.Business.FactorAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.Factor.Business.FactorNotFoundException;
import com.github.aoxter.ThatWasGreat.Factor.Business.FactorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.*;

public class FactorServiceTests {
    private FactorService factorService;
    private EntryRepository entryRepository;

    @BeforeEach
    void setupService() {
        entryRepository = Mockito.mock(EntryRepository.class);
        factorService = new FactorService(entryRepository);
    }

    @Test
    void addFactorCorrect() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Mockito.when(entryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Entry updatedEntry = factorService.addFactor(1L, "Factor C");
        Entry expectedEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, Map.of("Factor A", (byte)2, "Factor B", (byte)4, "Factor C", (byte) 0));
        Assertions.assertEquals(expectedEntry.getRates(), updatedEntry.getRates());
    }

    @Test
    void addFactorFailedBecauseFactorAlreadyExists() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(FactorAlreadyExistsException.class, () -> {
            factorService.addFactor(1L, "Factor B");
        });
    }

    @Test
    void addFactorFailedBecauseEntryNotFound() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(EntryNotFoundException.class, () -> {
            factorService.addFactor(2L, "Factor C");
        });
    }

    @Test
    void deleteFactorCorrect() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Mockito.when(entryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Entry updatedEntry = factorService.deleteFactor(1L, "Factor A");
        Entry expectedEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, Map.of("Factor B", (byte)4));
        Assertions.assertEquals(expectedEntry.getRates(), updatedEntry.getRates());
    }

    @Test
    void deleteFactorFailedBecauseFactorNotFound() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(FactorNotFoundException.class, () -> {
            factorService.deleteFactor(1L, "Factor C");
        });
    }

    @Test
    void deleteFactorFailedBecauseEntryNotFound() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(EntryNotFoundException.class, () -> {
            factorService.deleteFactor(2L, "Factor A");
        });
    }
}
