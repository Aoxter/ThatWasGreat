package com.github.aoxter.ThatWasGreat.WebLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryCanNotBeRemoved;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryService;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.RatingForm;
import com.github.aoxter.ThatWasGreat.Category.Web.CategoryController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

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
                new Category("Test Category 1", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor A", "Factor B", "Factor C")),
                new Category("Test Category 2", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor I", "Factor II", "Factor III"))
        );
        Mockito.when(categoryService.getAll()).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/category/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCategoryByIdRequestTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor A", "Factor B", "Factor C"));
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.get("/category/{id}", 1L)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createCategoryRequestTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor A", "Factor B", "Factor C"));
        Mockito.when(categoryService.add(ArgumentMatchers.any())).thenReturn(category);
        mockMvc.perform(MockMvcRequestBuilders.post("/category/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void updateCategoryRequestTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Mockito.when(categoryService.update(ArgumentMatchers.eq(1L), ArgumentMatchers.any())).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.put("/category/edit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteCategoryRequestTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(5L))).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/delete/{id}", 5L)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createCategoryRequestNameNotGivenValidationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/category/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ratingForm\": \"OneToTen\", \"factors\": [\"Factor A\"]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getCategoriesSerializationTest() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category("Test Category 1", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor A", "Factor B")),
                new Category("Test Category 2", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor I", "Factor II"))
        );
        Mockito.when(categoryService.getAll()).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/category/all", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Test Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].ratingForm").value("STARS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].factors[0]").value("Factor A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].factors[1]").value("Factor B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].entries").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Test Category 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].ratingForm").value("STARS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].factors[0]").value("Factor I"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].factors[1]").value("Factor II"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].entries").isEmpty());
    }

    @Test
    void getCategorySerializationTest() throws Exception {
        Category category = new Category("Test Category", "Lorem ipsum", RatingForm.getDefault(), Arrays.asList("Factor A", "Factor B"));
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(category));
        mockMvc.perform(MockMvcRequestBuilders.get("/category/{id}", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratingForm").value("STARS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.factors[0]").value("Factor A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.factors[1]").value("Factor B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries").isEmpty());
    }

    @Test
    void categoryCanNotBeRemovedExceptionTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Mockito.when(categoryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(category));
        Mockito.doThrow(new CategoryCanNotBeRemoved("Default categories can not be removed")).when(categoryService).delete(ArgumentMatchers.eq(1L));
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/delete/{id}", 1L)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
