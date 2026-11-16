package com.titan.tune.domain.service;

import com.titan.tune.application.dto.category.CategoryRequest;
import com.titan.tune.common.exception.BusinessException;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Category;
import com.titan.tune.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.findByNomCategorie(request.nomCategorie()).isPresent()) {
            throw new BusinessException("Cette catégorie existe déjà.");
        }
        Category category = Category.builder()
                .trackingId(UUID.randomUUID().toString())
                .nomCategorie(request.nomCategorie())
                .build();
        return categoryRepository.save(category);
    }

    public Category updateCategory(String id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));
        category.setNomCategorie(request.nomCategorie());
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));
    }

    public Category getByName(String name) {
        return categoryRepository.findByNomCategorie(name)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));
    }

    public List<Category> search(String keyword) {
        return categoryRepository.findByNomCategorieContainingIgnoreCase(keyword);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}