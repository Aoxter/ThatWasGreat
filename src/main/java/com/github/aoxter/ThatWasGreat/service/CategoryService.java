package com.github.aoxter.ThatWasGreat.service;

import com.github.aoxter.ThatWasGreat.model.Category;
import com.github.aoxter.ThatWasGreat.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> list(){
        return  categoryRepository.findAll();
    }

}
