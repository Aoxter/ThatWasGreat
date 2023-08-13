package com.github.aoxter.ThatWasGreat.service;

import com.github.aoxter.ThatWasGreat.model.Category;
import com.github.aoxter.ThatWasGreat.model.Entry;
import com.github.aoxter.ThatWasGreat.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Entry add(long categoryId, Entry entry) throws Exception {
        if(getAll(categoryId).stream().map(Entry::getName).collect(Collectors.toList()).contains(entry.getName())){
            throw new Exception("Entry with that name already exists in given category.");
        }
        Optional<Category> category = categoryService.getById(categoryId);
        if(category.isEmpty()){
            throw new Exception("Can not add new entry to given category because category of given ID doesn't exists.");
        }
        return entryRepository.save(new Entry(category.get(), entry.getName(), entry.getDescription()));
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
