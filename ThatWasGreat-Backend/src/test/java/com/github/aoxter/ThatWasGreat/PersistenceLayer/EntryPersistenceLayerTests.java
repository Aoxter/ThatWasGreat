package com.github.aoxter.ThatWasGreat.PersistenceLayer;

import com.github.aoxter.ThatWasGreat.Entry.Data.EntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
public class EntryPersistenceLayerTests {
    @Autowired
    private EntryRepository entryRepository;

    @Test
    void isEntryRepositoryLoadedCorrectly() {
        assertThat(entryRepository, notNullValue());
    }
}
