package it.magiavventure.mapper;

import it.magiavventure.mongo.entity.EStory;
import it.magiavventure.mongo.model.Category;
import it.magiavventure.mongo.model.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DisplayName("Story mapping tests")
class StoryMapperTest {

    private final StoryMapper storyMapper = Mappers.getMapper(StoryMapper.class);

    @Test
    @DisplayName("Given story entity map to story dto and get read time")
    void givenStoryEntity_mapToStoryDto_ok() {
        EStory storyEntity = EStory
                .builder()
                .id(UUID.randomUUID())
                .title("title")
                .subtitle("subtitle")
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sit amet varius lorem. Cras ultricies molestie mi, lacinia volutpat enim facilisis suscipit. Etiam ullamcorper a felis a faucibus. Sed tincidunt sodales eros tempor gravida. Nam dictum, massa sit amet ullamcorper venenatis, enim massa viverra risus, a suscipit quam velit ac ex. Quisque ac tortor vitae mi vulputate condimentum. Ut quis faucibus ex. Phasellus dictum lorem ante, vitae semper sem suscipit vitae. Morbi sodales neque fringilla, tincidunt augue nec, eleifend quam. Curabitur tristique urna elit, ac ultrices augue tempus vel.\n" +
                        "\n" +
                        "Sed et urna nibh. Maecenas eu euismod erat. Praesent posuere posuere aliquet. Vivamus non consequat odio, eu hendrerit elit. Aliquam vestibulum elit a turpis vehicula, non ornare nibh consectetur. Maecenas ullamcorper arcu vel enim tincidunt, ac blandit ante ultricies. Vivamus eget convallis erat. Morbi imperdiet nisi eu augue bibendum aliquam. Integer et ipsum venenatis, euismod velit vel, congue sapien.\n" +
                        "\n" +
                        "Nulla nec gravida diam. Sed eu consequat arcu. Vestibulum congue ante ut quam sollicitudin ornare. Aenean lorem justo, pretium vitae sagittis eget, rutrum id risus. Pellentesque id mauris ut nulla fermentum pulvinar. Maecenas faucibus risus mauris, sed volutpat sapien luctus eget. Morbi a consectetur velit. Quisque sodales, metus vehicula pretium aliquet, dolor odio fermentum ipsum, nec dapibus nulla dui vitae ante. Morbi nec rutrum elit. Aenean iaculis ut ipsum ut ultricies.\n" +
                        "\n" +
                        "Aliquam et mi non nulla sodales sagittis et ac nunc. Ut varius orci luctus dolor tempor tempor. Vestibulum maximus nisl eros, vel pretium urna accumsan eu. Etiam volutpat bibendum leo nec.")
                .author("author")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .background("background")
                        .name("name")
                        .build()))
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .active(true)
                .verified(true)
                .build();
        Story story = storyMapper.map(storyEntity);

        Assertions.assertNotNull(story);
        Assertions.assertEquals(storyEntity.getId(), story.getId());
        Assertions.assertEquals(storyEntity.getTitle(), story.getTitle());
        Assertions.assertEquals(storyEntity.getSubtitle(), story.getSubtitle());
        Assertions.assertEquals(storyEntity.getText(), story.getText());
        Assertions.assertEquals(storyEntity.getAuthor(), story.getAuthor());
        Assertions.assertIterableEquals(storyEntity.getCategories(), story.getCategories());
        Assertions.assertEquals(76, story.getReadTime());
    }

    @Test
    @DisplayName("Given story entity with null categories map to story dto and get read time ")
    void givenStoryEntityWithNullCategories_mapToStoryDto_ok() {
        EStory storyEntity = EStory
                .builder()
                .id(UUID.randomUUID())
                .title("title")
                .subtitle("subtitle")
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sit amet varius lorem. Cras ultricies molestie mi, lacinia volutpat enim facilisis suscipit. Etiam ullamcorper a felis a faucibus. Sed tincidunt sodales eros tempor gravida. Nam dictum, massa sit amet ullamcorper venenatis, enim massa viverra risus, a suscipit quam velit ac ex. Quisque ac tortor vitae mi vulputate condimentum. Ut quis faucibus ex. Phasellus dictum lorem ante, vitae semper sem suscipit vitae. Morbi sodales neque fringilla, tincidunt augue nec, eleifend quam. Curabitur tristique urna elit, ac ultrices augue tempus vel.\n" +
                        "\n" +
                        "Sed et urna nibh. Maecenas eu euismod erat. Praesent posuere posuere aliquet. Vivamus non consequat odio, eu hendrerit elit. Aliquam vestibulum elit a turpis vehicula, non ornare nibh consectetur. Maecenas ullamcorper arcu vel enim tincidunt, ac blandit ante ultricies. Vivamus eget convallis erat. Morbi imperdiet nisi eu augue bibendum aliquam. Integer et ipsum venenatis, euismod velit vel, congue sapien.\n" +
                        "\n" +
                        "Nulla nec gravida diam. Sed eu consequat arcu. Vestibulum congue ante ut quam sollicitudin ornare. Aenean lorem justo, pretium vitae sagittis eget, rutrum id risus. Pellentesque id mauris ut nulla fermentum pulvinar. Maecenas faucibus risus mauris, sed volutpat sapien luctus eget. Morbi a consectetur velit. Quisque sodales, metus vehicula pretium aliquet, dolor odio fermentum ipsum, nec dapibus nulla dui vitae ante. Morbi nec rutrum elit. Aenean iaculis ut ipsum ut ultricies.\n" +
                        "\n" +
                        "Aliquam et mi non nulla sodales sagittis et ac nunc. Ut varius orci luctus dolor tempor tempor. Vestibulum maximus nisl eros, vel pretium urna accumsan eu. Etiam volutpat bibendum leo nec.")
                .author("author")
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .active(true)
                .verified(true)
                .build();
        Story story = storyMapper.map(storyEntity);

        Assertions.assertNotNull(story);
        Assertions.assertEquals(storyEntity.getId(), story.getId());
        Assertions.assertEquals(storyEntity.getTitle(), story.getTitle());
        Assertions.assertEquals(storyEntity.getSubtitle(), story.getSubtitle());
        Assertions.assertEquals(storyEntity.getText(), story.getText());
        Assertions.assertEquals(storyEntity.getAuthor(), story.getAuthor());
        Assertions.assertNull(story.getCategories());
        Assertions.assertEquals(76, story.getReadTime());
    }

    @Test
    @DisplayName("Given null story entity map to story dto")
    void givenNullStoryEntity_mapToStoryDto_ok() {
        Story story = storyMapper.map(null);

        Assertions.assertNull(story);
    }

    @Test
    @DisplayName("Given story entity map partial to story dto trunc text and get read time")
    void givenStoryEntity_mapPartialToStoryDto_ok() {
        EStory storyEntity = EStory
                .builder()
                .id(UUID.randomUUID())
                .title("title")
                .subtitle("subtitle")
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sit amet varius lorem. Cras ultricies molestie mi, lacinia volutpat enim facilisis suscipit. Etiam ullamcorper a felis a faucibus. Sed tincidunt sodales eros tempor gravida. Nam dictum, massa sit amet ullamcorper venenatis, enim massa viverra risus, a suscipit quam velit ac ex. Quisque ac tortor vitae mi vulputate condimentum. Ut quis faucibus ex. Phasellus dictum lorem ante, vitae semper sem suscipit vitae. Morbi sodales neque fringilla, tincidunt augue nec, eleifend quam. Curabitur tristique urna elit, ac ultrices augue tempus vel.\n" +
                        "\n" +
                        "Sed et urna nibh. Maecenas eu euismod erat. Praesent posuere posuere aliquet. Vivamus non consequat odio, eu hendrerit elit. Aliquam vestibulum elit a turpis vehicula, non ornare nibh consectetur. Maecenas ullamcorper arcu vel enim tincidunt, ac blandit ante ultricies. Vivamus eget convallis erat. Morbi imperdiet nisi eu augue bibendum aliquam. Integer et ipsum venenatis, euismod velit vel, congue sapien.\n" +
                        "\n" +
                        "Nulla nec gravida diam. Sed eu consequat arcu. Vestibulum congue ante ut quam sollicitudin ornare. Aenean lorem justo, pretium vitae sagittis eget, rutrum id risus. Pellentesque id mauris ut nulla fermentum pulvinar. Maecenas faucibus risus mauris, sed volutpat sapien luctus eget. Morbi a consectetur velit. Quisque sodales, metus vehicula pretium aliquet, dolor odio fermentum ipsum, nec dapibus nulla dui vitae ante. Morbi nec rutrum elit. Aenean iaculis ut ipsum ut ultricies.\n" +
                        "\n" +
                        "Aliquam et mi non nulla sodales sagittis et ac nunc. Ut varius orci luctus dolor tempor tempor. Vestibulum maximus nisl eros, vel pretium urna accumsan eu. Etiam volutpat bibendum leo nec.")
                .author("author")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .background("background")
                        .name("name")
                        .build()))
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .active(true)
                .verified(true)
                .build();
        Story story = storyMapper.mapPartial(storyEntity);

        Assertions.assertNotNull(story);
        Assertions.assertEquals(storyEntity.getId(), story.getId());
        Assertions.assertEquals(storyEntity.getTitle(), story.getTitle());
        Assertions.assertEquals(storyEntity.getSubtitle(), story.getSubtitle());
        Assertions.assertEquals(storyEntity.getText().substring(0,100)+"...", story.getText());
        Assertions.assertEquals(storyEntity.getAuthor(), story.getAuthor());
        Assertions.assertIterableEquals(storyEntity.getCategories(), story.getCategories());
        Assertions.assertEquals(76, story.getReadTime());
    }

    @Test
    @DisplayName("Given story entity with null categories map partial to story dto and get read time ")
    void givenStoryEntityWithNullCategories_mapPartialToStoryDto_ok() {
        EStory storyEntity = EStory
                .builder()
                .id(UUID.randomUUID())
                .title("title")
                .subtitle("subtitle")
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sit amet varius lorem.")
                .author("author")
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .active(true)
                .verified(true)
                .build();
        Story story = storyMapper.mapPartial(storyEntity);

        Assertions.assertNotNull(story);
        Assertions.assertEquals(storyEntity.getId(), story.getId());
        Assertions.assertEquals(storyEntity.getTitle(), story.getTitle());
        Assertions.assertEquals(storyEntity.getSubtitle(), story.getSubtitle());
        Assertions.assertEquals(storyEntity.getText(), story.getText());
        Assertions.assertEquals(storyEntity.getAuthor(), story.getAuthor());
        Assertions.assertNull(story.getCategories());
        Assertions.assertEquals(4, story.getReadTime());
    }

    @Test
    @DisplayName("Given null story entity map partial to story dto")
    void givenNullStoryEntity_mapPartialToStoryDto_ok() {
        Story story = storyMapper.mapPartial(null);

        Assertions.assertNull(story);
    }


}
