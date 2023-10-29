package com.github.aoxter.ThatWasGreat.WebLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryNotFoundException;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Data.RatingForm;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryAlreadyExistsException;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryService;
import com.github.aoxter.ThatWasGreat.Entry.Data.Entry;
import com.github.aoxter.ThatWasGreat.Entry.Web.EntryController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
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
import static org.hamcrest.Matchers.notNullValue;

@WebMvcTest(EntryController.class)
public class EntryWebLayerTests {
    @Autowired
    private EntryController entryController;

    @MockBean
    private EntryService entryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void isEntryControllerLoadedCorrectly(){
        assertThat(entryController, notNullValue());
    }

    @Test
    void getAllEntriesRequestTest() throws Exception {
        List<Entry> entries = Arrays.asList(
                new Entry("Test Entry 1"),
                new Entry("Test Entry 2"));
        Mockito.when(entryService.getAll()).thenReturn(entries);
        mockMvc.perform(MockMvcRequestBuilders.get("/entry/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllEntriesInCategoryRequestTest() throws Exception {
        List<Entry> entries = Arrays.asList(
                new Entry("Test Entry 1"),
                new Entry("Test Entry 2"));
        Mockito.when(entryService.getAll(ArgumentMatchers.eq(1L))).thenReturn(entries);
        mockMvc.perform(MockMvcRequestBuilders.get("/entry/all?categoryId={id}", 1L)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getEntryByIdRequestTest() throws Exception {
        Entry entry = new Entry("Test Entry");
        Mockito.when(entryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(entry));
        mockMvc.perform(MockMvcRequestBuilders.get("/entry/{id}", 1L)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createEntryRequestTest() throws Exception {
        Entry entry = new Entry("Test Entry");
        Mockito.when(entryService.add(ArgumentMatchers.eq(1L), ArgumentMatchers.any(Entry.class))).thenReturn(entry);
        mockMvc.perform(MockMvcRequestBuilders.post("/entry/new?categoryId={id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entry)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void entryAlreadyExistsExceptionTest() throws Exception {
        Entry entry = new Entry("Test Entry");
        Mockito.doThrow(new EntryAlreadyExistsException("Entry with that name already exists in the given category.")).when(entryService).add(ArgumentMatchers.eq(1L), ArgumentMatchers.any(Entry.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/entry/new?categoryId={id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(entry)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void categoryNotFoundExceptionTest() throws Exception {
        Entry entry = new Entry("Test Entry");
        Mockito.doThrow(new CategoryNotFoundException("Can not add new entry because category of the given ID doesn't exists.")).when(entryService).add(ArgumentMatchers.eq(1L), ArgumentMatchers.any(Entry.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/entry/new?categoryId={id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(entry)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateEntryRequestTest() throws Exception {
        Entry entry = new Entry("Test Entry");
        Mockito.when(entryService.update(ArgumentMatchers.eq(1L), ArgumentMatchers.any(Entry.class))).thenReturn(Optional.of(entry));
        mockMvc.perform(MockMvcRequestBuilders.put("/entry/edit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(entry)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteEntryRequestTest() throws Exception {
        Entry entry = new Entry("Test Entry");
        Mockito.when(entryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(entry));
        mockMvc.perform(MockMvcRequestBuilders.delete("/entry/delete/{id}", 1L)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createEntryRequestNameNotGivenValidationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/entry/new?categoryId={id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Lorem ipsum\", \"overallRate\": 3}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getEntrySerializationTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Map<String, Byte> rates = new HashMap<>();
        rates.put("Factor A", (byte)2);
        rates.put("Factor B", (byte)4);
        Entry entry = new Entry(category,"Test Entry", "Lorem ipsum", (byte) 3, rates);
        Mockito.when(entryService.getById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(entry));
        mockMvc.perform(MockMvcRequestBuilders.get("/entry/{id}", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Entry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.overallRate").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rates.['Factor A']").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rates.['Factor B']").value(4));
    }
    @Test
    void getEntriesSerializationTest() throws Exception {
        Category category = new Category("Test Category", RatingForm.getDefault());
        Map<String, Byte> rates1 = new HashMap<>();
        rates1.put("Factor A", (byte)2);
        rates1.put("Factor B", (byte)4);
        Map<String, Byte> rates2 = new HashMap<>();
        rates2.put("Factor A", (byte)6);
        rates2.put("Factor B", (byte)10);
        List<Entry> entries = Arrays.asList(
                new Entry(category,"Test Entry 1", "Lorem ipsum", (byte) 3, rates1),
                new Entry(category,"Test Entry 2", "Lorem ipsum", (byte) 8, rates2));
        Mockito.when(entryService.getAll()).thenReturn(entries);
        mockMvc.perform(MockMvcRequestBuilders.get("/entry/all"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Test Entry 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].overallRate").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].rates.['Factor A']").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].rates.['Factor B']").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Test Entry 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].description").value("Lorem ipsum"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].category").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].overallRate").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].rates.['Factor A']").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].rates.['Factor B']").value(10));
    }
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
