package com.github.aoxter.ThatWasGreat.Category.Web;

import com.github.aoxter.ThatWasGreat.Category.Business.CategoryCanNotBeRemoved;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
import com.github.aoxter.ThatWasGreat.Category.Business.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAll();
            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
        try {
            Optional<Category> category = categoryService.getById(id);
            if (category.isPresent()) {
                return new ResponseEntity<>(category.get(), HttpStatus.OK);
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
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        try {
            Category _category = categoryService.add(category);
            return new ResponseEntity<>(_category, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long id, @Valid @RequestBody Category category) {
        try {
            Optional<Category> categoryUpdated = categoryService.update(id, category);
            if (categoryUpdated.isPresent()) {
                return new ResponseEntity<>(categoryUpdated.get(), HttpStatus.OK);
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
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
        try {
            Optional<Category> category = categoryService.getById(id);
            if (!category.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            categoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (CategoryCanNotBeRemoved categoryCanNotBeRemoved) {
            throw new CategoryCanNotBeRemoved(categoryCanNotBeRemoved.getMessage());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
