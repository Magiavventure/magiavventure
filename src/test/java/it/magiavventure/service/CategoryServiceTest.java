package it.magiavventure.service;

import it.magiavventure.common.error.MagiavventureException;
import it.magiavventure.mapper.CategoryMapper;
import it.magiavventure.model.category.CreateCategory;
import it.magiavventure.model.category.UpdateCategory;
import it.magiavventure.mongo.entity.ECategory;
import it.magiavventure.mongo.model.Category;
import it.magiavventure.mongo.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@DisplayName("Category service tests")
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryService self;
    @Mock
    private CategoryRepository categoryRepository;
    @Spy
    private CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    @Captor
    ArgumentCaptor<ECategory> eCategoryCaptor;
    @Captor
    ArgumentCaptor<Example<ECategory>> exampleArgumentCaptor;
    @Captor
    ArgumentCaptor<Sort> sortArgumentCaptor;

    @Test
    @DisplayName("Create category with name that not exists")
    void createCategory_ok_nameNotExists() {
        CreateCategory createCategory = CreateCategory
                .builder()
                .name("test")
                .background("background")
                .active(true)
                .build();
        ECategory eCategory = ECategory
                .builder()
                .id(UUID.randomUUID())
                .name("test")
                .background("background")
                .active(true)
                .build();

        Mockito.when(categoryRepository.save(eCategoryCaptor.capture()))
                .thenReturn(eCategory);
        Mockito.when(categoryRepository.exists(exampleArgumentCaptor.capture()))
                .thenReturn(false);

        Category category = categoryService.createCategory(createCategory);

        Mockito.verify(categoryRepository).save(eCategoryCaptor.capture());
        Mockito.verify(categoryRepository).exists(exampleArgumentCaptor.capture());
        ECategory categoryCapt = eCategoryCaptor.getValue();
        Example<ECategory> example = exampleArgumentCaptor.getValue();

        Assertions.assertNotNull(category);
        Assertions.assertEquals(createCategory.getName(), category.getName());
        Assertions.assertEquals(createCategory.getBackground(), category.getBackground());
        Assertions.assertEquals(createCategory.getName(), categoryCapt.getName());
        Assertions.assertEquals(createCategory.getBackground(), categoryCapt.getBackground());
        Assertions.assertNotNull(categoryCapt.getId());
        Assertions.assertTrue(categoryCapt.isActive());
        Assertions.assertEquals(createCategory.getName(), example.getProbe().getName());
    }

    @Test
    @DisplayName("Create category with name that already exists")
     void createCategory_ko_nameAlreadyExists() {
        CreateCategory createCategory = CreateCategory
                .builder()
                .name("test")
                .background("background")
                .active(true)
                .build();

        Mockito.when(categoryRepository.exists(exampleArgumentCaptor.capture()))
                .thenReturn(true);

        MagiavventureException exception = Assertions.assertThrows(MagiavventureException.class,
                () -> categoryService.createCategory(createCategory));

        Mockito.verify(categoryRepository).exists(exampleArgumentCaptor.capture());
        Example<ECategory> example = exampleArgumentCaptor.getValue();

        Assertions.assertEquals(createCategory.getName(), example.getProbe().getName());
        Assertions.assertEquals("category-exists", exception.getError().getKey());
        Assertions.assertEquals(1, exception.getError().getArgs().length);
    }

    @Test
    @DisplayName("Update category with name that not exists")
    void updateCategory_ok_nameNotExists() {
        UUID id = UUID.randomUUID();
        UpdateCategory updateCategory = UpdateCategory
                .builder()
                .id(id)
                .name("test 2")
                .background("background 2")
                .active(false)
                .build();
        ECategory eCategory = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .active(true)
                .build();
        ECategory eCategoryUpdated = ECategory
                .builder()
                .id(id)
                .name("test 2")
                .background("background 2")
                .active(false)
                .build();

        Mockito.when(self.findEntityById(id))
                .thenReturn(eCategory);
        Mockito.when(categoryRepository.save(eCategoryCaptor.capture()))
                .thenReturn(eCategoryUpdated);
        Mockito.when(categoryRepository.exists(exampleArgumentCaptor.capture()))
                .thenReturn(false);

        Category category = categoryService.updateCategory(updateCategory);

        Mockito.verify(self).findEntityById(id);
        Mockito.verify(categoryRepository).save(eCategoryCaptor.capture());
        Mockito.verify(categoryRepository).exists(exampleArgumentCaptor.capture());
        ECategory categoryCapt = eCategoryCaptor.getValue();
        Example<ECategory> example = exampleArgumentCaptor.getValue();

        Assertions.assertNotNull(category);
        Assertions.assertEquals(updateCategory.getName(), category.getName());
        Assertions.assertEquals(updateCategory.getBackground(), category.getBackground());
        Assertions.assertEquals(updateCategory.getName(), categoryCapt.getName());
        Assertions.assertEquals(updateCategory.getBackground(), categoryCapt.getBackground());
        Assertions.assertNotNull(categoryCapt.getId());
        Assertions.assertFalse(categoryCapt.isActive());
        Assertions.assertEquals(updateCategory.getName(), example.getProbe().getName());
    }

    @Test
    @DisplayName("Update category with same name")
    void updateCategory_ok_withSameName() {
        UUID id = UUID.randomUUID();
        UpdateCategory updateCategory = UpdateCategory
                .builder()
                .id(id)
                .name("test")
                .background("background 2")
                .active(true)
                .build();
        ECategory eCategory = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .active(true)
                .build();
        ECategory eCategoryUpdated = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background 2")
                .active(false)
                .build();

        Mockito.when(self.findEntityById(id))
                .thenReturn(eCategory);
        Mockito.when(categoryRepository.save(eCategoryCaptor.capture()))
                .thenReturn(eCategoryUpdated);

        Category category = categoryService.updateCategory(updateCategory);

        Mockito.verify(self).findEntityById(id);
        Mockito.verify(categoryRepository).save(eCategoryCaptor.capture());
        ECategory categoryCapt = eCategoryCaptor.getValue();

        Assertions.assertNotNull(category);
        Assertions.assertEquals(updateCategory.getName(), category.getName());
        Assertions.assertEquals(updateCategory.getBackground(), category.getBackground());
        Assertions.assertEquals(updateCategory.getName(), categoryCapt.getName());
        Assertions.assertEquals(updateCategory.getBackground(), categoryCapt.getBackground());
        Assertions.assertNotNull(categoryCapt.getId());
        Assertions.assertTrue(categoryCapt.isActive());
    }

    @Test
    @DisplayName("Update category with same name but not change status active")
    void updateCategory_ok_withSameNameButNotStatus() {
        UUID id = UUID.randomUUID();
        UpdateCategory updateCategory = UpdateCategory
                .builder()
                .id(id)
                .name("test")
                .background("background 2")
                .build();
        ECategory eCategory = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .active(true)
                .build();
        ECategory eCategoryUpdated = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background 2")
                .active(true)
                .build();

        Mockito.when(self.findEntityById(id))
                .thenReturn(eCategory);
        Mockito.when(categoryRepository.save(eCategoryCaptor.capture()))
                .thenReturn(eCategoryUpdated);

        Category category = categoryService.updateCategory(updateCategory);

        Mockito.verify(self).findEntityById(id);
        Mockito.verify(categoryRepository).save(eCategoryCaptor.capture());
        ECategory categoryCapt = eCategoryCaptor.getValue();

        Assertions.assertNotNull(category);
        Assertions.assertEquals(updateCategory.getName(), category.getName());
        Assertions.assertEquals(updateCategory.getBackground(), category.getBackground());
        Assertions.assertEquals(updateCategory.getName(), categoryCapt.getName());
        Assertions.assertEquals(updateCategory.getBackground(), categoryCapt.getBackground());
        Assertions.assertNotNull(categoryCapt.getId());
        Assertions.assertTrue(categoryCapt.isActive());
    }

    @Test
    @DisplayName("Find category by id")
    void findCategoryById_ok() {
        UUID id = UUID.randomUUID();
        ECategory eCategory = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .active(true)
                .build();

        Mockito.when(self.findEntityById(id))
                .thenReturn(eCategory);

        Category category = categoryService.findById(id);

        Mockito.verify(self).findEntityById(id);

        Assertions.assertNotNull(category);
        Assertions.assertEquals("test", category.getName());
        Assertions.assertEquals("background", category.getBackground());
    }

    @Test
    @DisplayName("Find all categories")
    void findAllCategories_ok() {
        ECategory eCategory = ECategory
                .builder()
                .id(UUID.randomUUID())
                .name("test")
                .background("background")
                .active(true)
                .build();
        List<ECategory> categoriesResponse = List.of(eCategory);

        Mockito.when(categoryRepository.findAll(sortArgumentCaptor.capture()))
                .thenReturn(categoriesResponse);

        List<Category> categories = categoryService.findAll();

        Mockito.verify(categoryRepository).findAll(sortArgumentCaptor.capture());

        Sort sort = sortArgumentCaptor.getValue();

        Assertions.assertNotNull(categories);
        Assertions.assertEquals(1, categories.size());
        Sort.Order order = sort.getOrderFor("name");
        Assertions.assertNotNull(order);
        Assertions.assertEquals(Sort.Direction.ASC, order.getDirection());
    }

    @Test
    @DisplayName("Delete category by id")
    void deleteCategoryById_ok() {
        UUID id = UUID.randomUUID();
        ECategory eCategory = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .active(true)
                .build();

        Mockito.when(self.findEntityById(id))
                .thenReturn(eCategory);
        Mockito.doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteById(id);

        Mockito.verify(self).findEntityById(id);
        Mockito.verify(categoryRepository).deleteById(id);
    }

    @Test
    @DisplayName("Given id find category entity")
    void givenId_findEntityCategory_ok() {
        UUID id = UUID.randomUUID();
        ECategory eCategory = ECategory
                .builder()
                .id(id)
                .name("test")
                .background("background")
                .active(true)
                .build();

        Mockito.when(categoryRepository.findById(id))
                .thenReturn(Optional.of(eCategory));

        ECategory foundCategory = categoryService.findEntityById(id);

        Mockito.verify(categoryRepository).findById(id);

        Assertions.assertNotNull(foundCategory);
        Assertions.assertEquals(eCategory, foundCategory);
    }

    @Test
    @DisplayName("Given id find category entity but is not found")
    void givenId_findEntityCategory_notFound() {
        UUID id = UUID.randomUUID();

        Mockito.when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());

        MagiavventureException exception = Assertions.assertThrows(MagiavventureException.class,
                () -> categoryService.findEntityById(id));

        Mockito.verify(categoryRepository).findById(id);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("category-not-found", exception.getError().getKey());
        Assertions.assertIterableEquals(List.of(id.toString()), Arrays.asList(exception.getError().getArgs()));
    }
}
