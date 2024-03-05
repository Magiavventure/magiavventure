package it.magiavventure.mapper;

import it.magiavventure.mongo.entity.ECategory;
import it.magiavventure.mongo.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

@DisplayName("Category mapping tests")
class CategoryMapperTest {

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    @DisplayName("Map category entity in category dto")
    void mapCategoryEntity() {
        ECategory categoryEntity = ECategory
                .builder()
                .id(UUID.randomUUID())
                .name("test")
                .background("background")
                .active(true)
                .createdDate(LocalDateTime.now())
                .build();
        Category category = categoryMapper.map(categoryEntity);

        Assertions.assertNotNull(category);
        Assertions.assertEquals(categoryEntity.getId(), category.getId());
        Assertions.assertEquals(categoryEntity.getName(), category.getName());
        Assertions.assertEquals(categoryEntity.getBackground(), category.getBackground());
    }

    @Test
    @DisplayName("Map category entity null in category dto")
    void mapCategoryEntityNull() {
        Category category = categoryMapper.map(null);

        Assertions.assertNull(category);
    }


}
