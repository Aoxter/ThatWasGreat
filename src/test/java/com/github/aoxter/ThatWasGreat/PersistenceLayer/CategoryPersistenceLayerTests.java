package com.github.aoxter.ThatWasGreat.PersistenceLayer;

import com.github.aoxter.ThatWasGreat.Category.Data.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
public class CategoryPersistenceLayerTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void categoryRepositoryIsLoadedCorrectly() {
        assertThat(categoryRepository, notNullValue());
    }
}
