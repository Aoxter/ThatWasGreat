package com.github.aoxter.ThatWasGreat.Factor.Business;

import com.github.aoxter.ThatWasGreat.Entry.Business.EntryNotFoundException;
import com.github.aoxter.ThatWasGreat.Entry.Data.Entry;
import com.github.aoxter.ThatWasGreat.Entry.Data.EntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FactorService {
    private final EntryRepository entryRepository;

    public FactorService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Entry addFactor(Long id, String factor) {
        return addFactor(id, factor, (byte) 0);
    }

    public Entry addFactor(Long id, String factor, byte value) throws FactorAlreadyExistsException, EntryNotFoundException {
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

    public Entry deleteFactor(Long id, String factor) throws FactorNotFoundException, EntryNotFoundException {
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
}
