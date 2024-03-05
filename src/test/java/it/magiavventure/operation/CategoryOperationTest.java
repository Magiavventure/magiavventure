package it.magiavventure.operation;

import it.magiavventure.model.category.CreateCategory;
import it.magiavventure.model.category.UpdateCategory;
import it.magiavventure.mongo.model.Category;
import it.magiavventure.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@DisplayName("Category operation tests")
class CategoryOperationTest {
    @InjectMocks
    private CategoryOperation categoryOperation;
    @Mock
    private CategoryService categoryService;

    @Test
    @DisplayName("Create category test with name that not exists")
    void createCategoryTest_ok() {
        CreateCategory createCategory = CreateCategory
                .builder()
                .name("test")
                .background("background")
                .active(true)
                .build();
        Category category = Category
                .builder()
                .id(UUID.randomUUID())
                .name("test")
                .background("background")
                .build();

        Mockito.when(categoryService.createCategory(createCategory))
                .thenReturn(category);

        Category categoryResponse = categoryOperation.createCategory(createCategory);

        Mockito.verify(categoryService).createCategory(createCategory);

        Assertions.assertNotNull(categoryResponse);
        Assertions.assertNotNull(categoryResponse.getId());
        Assertions.assertEquals(createCategory.getName(), categoryResponse.getName());
        Assertions.assertEquals(createCategory.getBackground(), categoryResponse.getBackground());
    }

    @Test
    @DisplayName("Find all categories test")
    void findAllCategoriesTest_ok() {

        Category category = Category
                .builder()
                .id(UUID.randomUUID())
                .name("test")
                .background("background")
                .build();
        List<Category> categories = List.of(category);

        Mockito.when(categoryService.findAll())
                .thenReturn(categories);

        List<Category> categoriesResponse = categoryOperation.retrieveCategories();

        Mockito.verify(categoryService).findAll();

        Assertions.assertNotNull(categoriesResponse);
        Assertions.assertEquals(1, categoriesResponse.size());
        categoriesResponse
                .stream()
                .findFirst()
                .ifPresent(c -> {
                    Assertions.assertEquals(category.getName(), c.getName());
                    Assertions.assertEquals(category.getBackground(), c.getBackground());
                    Assertions.assertEquals(category.getId(), c.getId());
                });
    }

    @Test
    @DisplayName("Find category by id test")
    void findCategoryByIdTest_ok() {

        UUID id = UUID.randomUUID();
        Category category = Category
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .build();

        Mockito.when(categoryService.findById(id))
                .thenReturn(category);

        Category categoryResponse = categoryOperation.retrieveCategory(id);

        Mockito.verify(categoryService).findById(id);

        Assertions.assertNotNull(categoryResponse);
        Assertions.assertEquals(category.getName(), categoryResponse.getName());
        Assertions.assertEquals(category.getBackground(), categoryResponse.getBackground());
        Assertions.assertEquals(category.getId(), categoryResponse.getId());
    }

    @Test
    @DisplayName("Update category test with name that not exists")
    void updateCategoryTest_ok() {
        UUID id = UUID.randomUUID();
        UpdateCategory updateCategory = UpdateCategory
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .active(false)
                .build();
        Category category = Category
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .build();

        Mockito.when(categoryService.updateCategory(updateCategory))
                .thenReturn(category);

        Category categoryResponse = categoryOperation.updateCategory(updateCategory);

        Mockito.verify(categoryService).updateCategory(updateCategory);

        Assertions.assertNotNull(categoryResponse);
        Assertions.assertEquals(updateCategory.getId(), categoryResponse.getId());
        Assertions.assertEquals(updateCategory.getName(), categoryResponse.getName());
        Assertions.assertEquals(updateCategory.getBackground(), categoryResponse.getBackground());
    }

    @Test
    @DisplayName("Delete category by id test")
    void deleteCategoryByIdTest_ok() {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(categoryService).deleteById(id);

        categoryOperation.deleteCategory(id);

        Mockito.verify(categoryService).deleteById(id);
    }
}
