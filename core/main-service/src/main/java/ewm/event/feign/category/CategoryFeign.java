package ewm.event.feign.category;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "category-service", fallback = CategoryFeignFallback.class)
public interface CategoryFeign {
    @GetMapping("/categories/{id}")
    CategoryDto getCategoryById(@PathVariable Long id);

    @PostMapping("/categories/list")
    Map<Long, CategoryDto> getCategoryById(@RequestBody Set<Long> categoriesId);
}
