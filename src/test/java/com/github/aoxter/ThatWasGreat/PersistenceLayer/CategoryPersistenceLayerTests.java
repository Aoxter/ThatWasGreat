package com.github.aoxter.ThatWasGreat.PersistenceLayer;

import com.github.aoxter.ThatWasGreat.Category.Data.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryPersistenceLayerTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void isCategoryRepositoryLoadedCorrectly() {
        assertThat(categoryRepository, notNullValue());
    }
}
