package com.github.aoxter.ThatWasGreat.repository;

import com.github.aoxter.ThatWasGreat.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByCategoryId(long id);

}
