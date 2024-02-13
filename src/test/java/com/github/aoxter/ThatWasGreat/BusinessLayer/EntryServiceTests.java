package com.github.aoxter.ThatWasGreat.BusinessLayer;

//TODO the package of the test class should match the package of the source class whose unit of source code it’ll test

import com.github.aoxter.ThatWasGreat.Category.Business.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryService;
import com.github.aoxter.ThatWasGreat.Category.Business.FactorAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.Category.Business.FactorNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.RatingForm;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryNotFoundException;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryService;
import com.github.aoxter.ThatWasGreat.Entry.Data.Entry;
import com.github.aoxter.ThatWasGreat.Entry.Data.EntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.*;


public class EntryServiceTests {
    private EntryRepository entryRepository;
    private CategoryService categoryService;
    private EntryService entryService;

    @BeforeEach
    void setupService() {
        entryRepository = Mockito.mock(EntryRepository.class);
        categoryService = Mockito.mock(CategoryService.class);
        entryService = new EntryService(entryRepository, categoryService);
    }

    @Test
    void addEntryCorrect() {
        Entry newEntry = new Entry("Test Entry", "Lorem ipsum");
        Entry existingEntry = new Entry("Old Test Entry", "Lorem ipsum");
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        List<Entry> entriesInCategory = Arrays.asList(existingEntry);
        Map<String, Byte> newRates = new HashMap<>();
        newRates.put("Factor A", (byte)0);
        newRates.put("Factor B", (byte)0);

        Mockito.when(entryRepository.findByCategoryId(1L)).thenReturn(entriesInCategory);
        Mockito.when(categoryService.getById(1L)).thenReturn(Optional.of(category));
        Mockito.when(entryRepository.save(ArgumentMatchers.any(Entry.class))).then(AdditionalAnswers.returnsFirstArg());

        Entry addedEntry = entryService.add(1L, newEntry);
        Assertions.assertEquals(category, addedEntry.getCategory());
        Assertions.assertEquals(newEntry.getName(), addedEntry.getName());
        Assertions.assertEquals(newEntry.getDescription(), addedEntry.getDescription());
        Assertions.assertEquals((byte)0, addedEntry.getOverallRate());
        Assertions.assertEquals(newRates, addedEntry.getRates());
    }

    @Test
    void addEntryFailedBecauseEntryExists() {
        Entry newEntry = new Entry("Test Entry", "Lorem ipsum");
        List<Entry> entriesInCategory = Arrays.asList(newEntry);
        Mockito.when(entryRepository.findByCategoryId(1L)).thenReturn(entriesInCategory);
        Assertions.assertThrows(EntryAlreadyExistsException.class, () -> {
            entryService.add(1L, newEntry);
        });
    }

    @Test
    void addEntryFailedBecauseMissingCategory() {
        Entry newEntry = new Entry("Test Entry", "Lorem ipsum");
        Entry existingEntry = new Entry("Old Test Entry", "Lorem ipsum");
        List<Entry> entriesInCategory = Arrays.asList(existingEntry);
        Mockito.when(entryRepository.findByCategoryId(1L)).thenReturn(entriesInCategory);
        Mockito.when(categoryService.getById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            entryService.add(1L, newEntry);
        });
    }

    @Test
    void updateEntryCorrect() {
        Entry newEntry = new Entry("Test Entry", "Lorem ipsum");
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.empty());
        Optional<Entry> updatedEntry = entryService.update(1L, newEntry);
        Assertions.assertEquals(updatedEntry, Optional.empty());
    }

    @Test
    void updateEntryFailedBecauseEntryNotExists() {
        Entry newEntry = new Entry("Test Entry");
        Entry existingEntry = new Entry("Old Test Entry", "Lorem ipsum");
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(existingEntry));
        Mockito.when(entryRepository.save(ArgumentMatchers.any(Entry.class))).then(AdditionalAnswers.returnsFirstArg());
        Entry updatedEntry = entryService.update(1L, newEntry).get();
        Assertions.assertEquals(newEntry.getName(), updatedEntry.getName());
        Assertions.assertEquals(null, updatedEntry.getDescription());
    }

    @Test
    void addFactorCorrect() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Mockito.when(entryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Entry updatedEntry = entryService.addFactor(1L, "Factor C");
        Entry expectedEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, Map.of("Factor A", (byte)2, "Factor B", (byte)4, "Factor C", (byte) 0));
        Assertions.assertEquals(expectedEntry.getRates(), updatedEntry.getRates());
    }

    @Test
    void addFactorFailedBecauseFactorAlreadyExists() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(FactorAlreadyExistsException.class, () -> {
            entryService.addFactor(1L, "Factor B");
        });
    }

    @Test
    void addFactorFailedBecauseEntryNotFound() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(EntryNotFoundException.class, () -> {
            entryService.addFactor(2L, "Factor C");
        });
    }

    @Test
    void deleteFactorCorrect() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Mockito.when(entryRepository.save(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
        Entry updatedEntry = entryService.deleteFactor(1L, "Factor A");
        Entry expectedEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, Map.of("Factor B", (byte)4));
        Assertions.assertEquals(expectedEntry.getRates(), updatedEntry.getRates());
    }

    @Test
    void deleteFactorFailedBecauseFactorNotFound() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(FactorNotFoundException.class, () -> {
            entryService.deleteFactor(1L, "Factor C");
        });
    }

    @Test
    void deleteFactorFailedBecauseEntryNotFound() {
        Category category = new Category("Test Category", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        Entry oldEntry = new Entry(category, "Entry", "Lorem ipsum", (byte)3, new HashMap<>(Map.of("Factor A", (byte)2, "Factor B", (byte)4)));
        Mockito.when(entryRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(oldEntry));
        Assertions.assertThrows(EntryNotFoundException.class, () -> {
            entryService.deleteFactor(2L, "Factor A");
        });
    }
}
