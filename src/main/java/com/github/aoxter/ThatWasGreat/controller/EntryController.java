package com.github.aoxter.ThatWasGreat.controller;

import com.github.aoxter.ThatWasGreat.exceptions.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.exceptions.EntryAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.model.Entry;
import com.github.aoxter.ThatWasGreat.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    EntryService entryService;

    @GetMapping("/all")
    public ResponseEntity<List<Entry>> getAllEntries(@RequestParam Optional<Long> categoryId) {
        try {
            List<Entry> entries;
            if(categoryId.isPresent()){
                entries = entryService.getAll(categoryId.get());
            }
            else{
                entries = entryService.getAll();
            }
            if (entries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable("id") long id) {
        try {
            Optional<Entry> entry = entryService.getById(id);
            if (entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Entry> createEntry(@RequestParam long categoryId, @RequestBody Entry entry) {
        try {
            Entry _entry = entryService.add(categoryId, entry);
            return new ResponseEntity<>(_entry, HttpStatus.CREATED);
        }
        catch (EntryAlreadyExistsException entryServiceException) {
            throw new EntryAlreadyExistsException(entryServiceException.getMessage());
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            throw new CategoryNotFoundException(categoryNotFoundException.getMessage());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable("id") long id, @RequestBody Entry entry) {
        try {
            Optional<Entry> entryUpdated = entryService.update(id, entry);
            if (entryUpdated.isPresent()) {
                return new ResponseEntity<>(entryUpdated.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEntry(@PathVariable("id") long id) {
        try {
            Optional<Entry> entry = entryService.getById(id);
            if (!entry.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            entryService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
