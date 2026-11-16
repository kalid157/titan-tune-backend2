package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.category.CategoryRequest;
import com.titan.tune.domain.model.Category;
import com.titan.tune.domain.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    @PutMapping("/update/{trackingId}")
    public ResponseEntity<Category> update(@PathVariable String trackingId, @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(trackingId, request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/get/{trackingId}")
    public ResponseEntity<Category> getById(@PathVariable String trackingId) {
        return ResponseEntity.ok(categoryService.getById(trackingId));
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Category> getByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getByName(name));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Category>> search(@PathVariable String keyword) {
        return ResponseEntity.ok(categoryService.search(keyword));
    }

    @DeleteMapping("/delete/{trackingId}")
    public ResponseEntity<Void> delete(@PathVariable String trackingId) {
        categoryService.deleteCategory(trackingId);
        return ResponseEntity.noContent().build();
    }
}