package com.github.aoxter.ThatWasGreat.WebLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryCanNotBeRemovedException;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryService;
import com.github.aoxter.ThatWasGreat.Category.Business.FactorNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.RatingForm;
import com.github.aoxter.ThatWasGreat.Category.Web.CategoryController;
import com.github.aoxter.ThatWasGreat.Entry.Data.Entry;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryWebLayerTests {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void isCategoryControllerLoadedCorrectly(){
        assertThat(categoryController, notNullValue());
    }

//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity()) 1
//                .build();
//    }

    @Test
    void getAllCategoriesRequestTest() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category("Test Category 1", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B", "Factor C"))),
                new Category("Test Category 2", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor I", "Factor II", "Factor III")))
        );
        Mockito.when(categoryService.getAll()).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/category/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCategoriesSerializationTest() throws Exception {
        Category category1 = new Category("Test Category 1", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        category1.setEntries(Arrays.asList(new Entry("Test Entry 1"), new Entry("Test Entry 2")));
        Category category2 = new Category("Test Category 2", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor I", "Factor II")));
        category2.setEntries(Arrays.asList(new Entry("Test Entry 3")));
        List<Category> categories = Arrays.asList(category1, category2);
        Mockito.when(categoryService.getAll()).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/category/all", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Test Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].ratingForm").value("STARS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].factors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].factors", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].factors", hasItem("Factor A")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].factors", hasItem("Factor B")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].entries").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].entries", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].entries", hasItem("Test Entry 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].entries", hasItem("Test Entry 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Test Category 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].ratingForm").value("STARS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].factors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].factors", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].factors", hasItem("Factor I")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].factors", hasItem("Factor II")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].entries").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].entries", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].entries", hasItem("Test Entry 3")));
    }

    @Test
    void getCategoryByIdRequestTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B", "Factor C")));
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.get("/category/{id}", 1L)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCategorySerializationTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B")));
        category.setEntries(Arrays.asList(new Entry("Test Entry 1"), new Entry("Test Entry 2")));
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.get("/category/{id}", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratingForm").value("STARS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.factors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.factors", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.factors", hasItem("Factor A")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.factors", hasItem("Factor B")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries", hasItem("Test Entry 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries", hasItem("Test Entry 2")));
    }

    @Test
    void createCategoryRequestTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A", "Factor B", "Factor C")));
        Mockito.when(categoryService.add(ArgumentMatchers.any())).thenReturn(category);
        mockMvc.perform(MockMvcRequestBuilders.post("/category/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createCategoryRequestNameNotGivenValidationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/category/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ratingForm\": \"OneToTen\", \"factors\": [\"Factor A\"]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateCategoryRequestTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Mockito.when(categoryService.update(ArgumentMatchers.eq(1L), ArgumentMatchers.any())).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.put("/category/{id}/edit", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteCategoryRequestTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(5L))).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/{id}/delete", 5L)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void categoryCanNotBeRemovedExceptionTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(category));
        Mockito.doThrow(new CategoryCanNotBeRemovedException("Default categories can not be removed")).when(categoryService).delete(ArgumentMatchers.eq(1L));
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/{id}/delete", 1L)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addFactorRequestTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A")));
        Mockito.when(categoryService.getById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.put("/category/{id}/factor/new?factor={factor}", 1L, "Factor B"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void categoryNotFoundExceptionTest() throws Exception {
        Mockito.when(categoryService.getById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.put("/category/{id}/factor/new?factor={factor}", 1L, "Factor B"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void factorAlreadyExistsExceptionTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A")));
        Mockito.when(categoryService.getById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Mockito.doThrow(new CategoryNotFoundException("Can not add new factor because category of the given ID doesn't exists."))
                .when(categoryService).addFactor(ArgumentMatchers.any(), ArgumentMatchers.any());
        mockMvc.perform(MockMvcRequestBuilders.put("/category/{id}/factor/new?factor={factor}", 1L, "Factor B"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteFactorRequestTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A")));
        Mockito.when(categoryService.getById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/{id}/factor/delete?factor={factor}", 1L, "Factor A"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void factorNotFoundExceptionTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A")));
        Mockito.when(categoryService.getById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        Mockito.doThrow(new FactorNotFoundException("This factor doesn't exists in the category of the given ID."))
                .when(categoryService).deleteFactor(ArgumentMatchers.any(), ArgumentMatchers.any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/{id}/factor/delete?factor={factor}", 1L, "Factor A"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void renameFactorRequestTest() throws  Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), new HashSet<>(Arrays.asList("Factor A")));
        Mockito.when(categoryService.getById(ArgumentMatchers.any())).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.put("/category/{id}/factor/rename?oldFactor={oldFactor}&newFactor={newFactor}", 1L, "Factor A", "Factor B"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
