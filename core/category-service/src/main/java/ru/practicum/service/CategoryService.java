package ru.practicum.service;

import ru.practicum.dto.NewCategoryDto;
import ru.practicum.dto.category.CategoryDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CategoryService {
    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto getCategoryById(Long id);

    Map<Long, CategoryDto> getCategoryById(Set<Long> categoriesId);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long id);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
}