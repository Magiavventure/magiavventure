package it.magiavventure.operation;

import it.magiavventure.model.category.CreateCategory;
import it.magiavventure.model.category.UpdateCategory;
import it.magiavventure.mongo.model.Category;
import it.magiavventure.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CategoryOperation {

    private final CategoryService categoryService;

    @PostMapping("/saveCategory")
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody @Valid CreateCategory createCategory) {
        return categoryService.createCategory(createCategory);
    }

    @GetMapping("/retrieveCategories")
    public List<Category> retrieveCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/retrieveCategory/{id}")
    public Category retrieveCategory(@PathVariable(name = "id") UUID id) {
        return categoryService.findById(id);
    }

    @PutMapping("/updateCategory")
    public Category updateCategory(@RequestBody @Valid UpdateCategory updateCategory) {
        return categoryService.updateCategory(updateCategory);
    }

    @DeleteMapping("/deleteCategory/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(name = "id") UUID id) {
        categoryService.deleteById(id);
    }
}
