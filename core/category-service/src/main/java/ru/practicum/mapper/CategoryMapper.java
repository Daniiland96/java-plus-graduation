package ru.practicum.mapper;

import ru.practicum.dto.NewCategoryDto;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryDto> toCategoryDtoList(List<Category> categories);

    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toCategoryByNew(NewCategoryDto newCategoryDto);
}
