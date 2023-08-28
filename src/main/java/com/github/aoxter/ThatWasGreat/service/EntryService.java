package com.github.aoxter.ThatWasGreat.service;

import com.github.aoxter.ThatWasGreat.exceptions.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.exceptions.EntryAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.model.Category;
import com.github.aoxter.ThatWasGreat.model.Entry;
import com.github.aoxter.ThatWasGreat.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private CategoryService categoryService;

    public List<Entry> getAll(){
        return  entryRepository.findAll();
    }

    public List<Entry> getAll(long id){
        return  entryRepository.findByCategoryId(id);
    }

    public Optional<Entry> getById(Long id) {
        return entryRepository.findById(id);
    }

    public Entry add(long categoryId, Entry entry) throws EntryAlreadyExistsException, CategoryNotFoundException {
        if(getAll(categoryId).stream().map(Entry::getName).collect(Collectors.toList()).contains(entry.getName())){
            throw new EntryAlreadyExistsException("Entry with that name already exists in the given category.");
        }
        Optional<Category> category = categoryService.getById(categoryId);
        if(category.isEmpty()){
            throw new CategoryNotFoundException("Can not add new entry because category of the given ID doesn't exists.");
        }
        return entryRepository.save(new Entry(category.get(), entry.getName(), entry.getDescription(), (byte)0, getRatesMapByCategory(category.get())));
    }

    private Map<String, Byte> getRatesMapByCategory(Category category) {
        Map<String, Byte> rates = new HashMap<>();
        for(String factor : category.getFactors()) {
            rates.put(factor, (byte)0);
        }
        return rates;
    }

    public Optional<Entry> update(Long id, Entry newEntryData) {
        Optional<Entry> entryToUpdate = entryRepository.findById(id);
        if (entryToUpdate.isPresent()) {
            Entry entryUpdated = entryToUpdate.get();
            entryUpdated.setName(newEntryData.getName());
            entryUpdated.setDescription(newEntryData.getDescription());
            entryUpdated.setOverallRate(newEntryData.getOverallRate());
            entryUpdated.setRates(newEntryData.getRates());
            return Optional.of(entryRepository.save(entryUpdated));
        } else {
            return Optional.empty();
        }
    }

    public void delete(Long id) throws Exception {
        entryRepository.deleteById(id);
    }
}
