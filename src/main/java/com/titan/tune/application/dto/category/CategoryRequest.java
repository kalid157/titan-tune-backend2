package com.titan.tune.application.dto.category;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank String nomCategorie) {}