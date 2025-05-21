package ewm.event.feign.category;

import ewm.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

@Component
public class CategoryFeignFallback implements CategoryFeign {
    @Override
    public CategoryDto getCategoryById(@PathVariable Long id) {
        throw new EntityNotFoundException(CategoryDto.class, "Не найдена категория с id: " + id);
    }

    @Override
    public Map<Long, CategoryDto> getCategoryById(@RequestBody Set<Long> categoriesId) {
        throw new EntityNotFoundException(CategoryDto.class, "Не найдены категории с id: " + categoriesId);
    }
}