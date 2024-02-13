package com.github.aoxter.ThatWasGreat.Entry.Business;

import com.github.aoxter.ThatWasGreat.Category.Business.CategoryService;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Business.FactorAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.Category.Business.FactorNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Entry.Data.Entry;
import com.github.aoxter.ThatWasGreat.Entry.Data.EntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntryService {
    private final EntryRepository entryRepository;
    private final CategoryService categoryService;

    public EntryService(EntryRepository entryRepository, CategoryService categoryService) {
        this.entryRepository = entryRepository;
        this.categoryService = categoryService;
    }

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

    public Entry addFactor(Long id, String factor) {
        return addFactor(id, factor, (byte) 0);
    }

    public Entry addFactor(Long id, String factor, byte value) {
        Optional<Entry> entryToUpdate = entryRepository.findById(id);
        if (!entryToUpdate.isPresent()) {
            throw new EntryNotFoundException("");
        }
        Entry entryUpdated = entryToUpdate.get();
        if(entryUpdated.getRates().get(factor) != null) {
            throw new FactorAlreadyExistsException(String.format("Attempted to add to rates of entry of id %d a factor that has already been added", id));
        }
        entryUpdated.getRates().put(factor, value);
        return entryRepository.save(entryUpdated);
    }

    public Entry deleteFactor(Long id, String factor) {
        Optional<Entry> entryToUpdate = entryRepository.findById(id);
        if (!entryToUpdate.isPresent()) {
            throw new EntryNotFoundException("");
        }
        Entry entryUpdated = entryToUpdate.get();
        if(entryUpdated.getRates().remove(factor) == null) {
            throw new FactorNotFoundException(String.format("Attempted to remove from rates of entry of id %d a factor that doesn't exists", id));
        }
        return entryRepository.save(entryUpdated);
    }

    public void delete(Long id) throws Exception {
        entryRepository.deleteById(id);
    }
}
