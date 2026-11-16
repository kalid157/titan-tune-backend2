package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByNomCategorie(String nomCategorie);
    List<Category> findByNomCategorieContainingIgnoreCase(String keyword);
}