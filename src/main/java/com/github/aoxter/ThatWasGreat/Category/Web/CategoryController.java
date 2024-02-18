package com.github.aoxter.ThatWasGreat.Category.Web;

import com.github.aoxter.ThatWasGreat.Category.Business.*;
import com.github.aoxter.ThatWasGreat.Category.Data.Category;
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/edit")
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

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
        try {
            Optional<Category> category = categoryService.getById(id);
            if (!category.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            categoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (CategoryCanNotBeRemovedException categoryCanNotBeRemovedException) {
            throw new CategoryCanNotBeRemovedException(categoryCanNotBeRemovedException.getMessage());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{categoryId}/factor/new")
    public ResponseEntity<Category> addFactor(@PathVariable("categoryId") long categoryId, @RequestParam String factor) {
        try {
            Optional<Category> category = categoryService.getById(categoryId);
            if (!category.isPresent()) {
                throw new CategoryNotFoundException("Can not add new factor because category of the given ID doesn't exists.");
            }
            Category categoryUpdated = categoryService.addFactor(categoryId, factor);
            return new ResponseEntity<>(categoryUpdated, HttpStatus.CREATED);
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            throw new CategoryNotFoundException(categoryNotFoundException.getMessage());
        }
        catch (FactorAlreadyExistsException factorAlreadyExistsException) {
            throw new FactorAlreadyExistsException(factorAlreadyExistsException.getMessage());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{categoryId}/factor/delete")
    public ResponseEntity<Category> deleteFactor(@PathVariable("categoryId") long categoryId, @RequestParam String factor) {
        try {
            Optional<Category> category = categoryService.getById(categoryId);
            if (!category.isPresent()) {
                throw new CategoryNotFoundException("Can not delete factor because category of the given ID doesn't exists.");
            }
            Category categoryUpdated = categoryService.deleteFactor(categoryId, factor);
            return new ResponseEntity<>(categoryUpdated, HttpStatus.OK);
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            throw new CategoryNotFoundException(categoryNotFoundException.getMessage());
        }
        catch (FactorNotFoundException factorNotFoundException) {
            throw new FactorNotFoundException(factorNotFoundException.getMessage());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{categoryId}/factor/rename")
    public ResponseEntity<Category> renameFactor(@PathVariable("categoryId") long categoryId, @RequestParam String oldFactor, @RequestParam String newFactor) {
        try {
            Optional<Category> category = categoryService.getById(categoryId);
            if (!category.isPresent()) {
                throw new CategoryNotFoundException("Can not rename factor because category of the given ID doesn't exists.");
            }
            Category categoryUpdated = categoryService.renameFactor(categoryId, oldFactor, newFactor);
            return new ResponseEntity<>(categoryUpdated, HttpStatus.OK);
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            throw new CategoryNotFoundException(categoryNotFoundException.getMessage());
        }
        catch (FactorNotFoundException factorNotFoundException) {
            throw new FactorNotFoundException(factorNotFoundException.getMessage());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
