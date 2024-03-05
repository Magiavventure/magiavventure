package it.magiavventure.operation;

import it.magiavventure.model.story.CreateStory;
import it.magiavventure.model.story.UpdateStory;
import it.magiavventure.mongo.model.Category;
import it.magiavventure.mongo.model.Story;
import it.magiavventure.service.StoryService;
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
class StoryOperationTest {

    @InjectMocks
    private StoryOperation storyOperation;
    @Mock
    private StoryService storyService;

    @Test
    @DisplayName("Given createStory object create story successfully")
    void givenCreateStory_createStory_ok() {
        CreateStory createStory = CreateStory
                .builder()
                .title("title")
                .subtitle("subtitle")
                .text("text")
                .categories(List.of(Category
                        .builder()
                                .id(UUID.randomUUID())
                                .name("name")
                                .background("background")
                        .build()))
                .build();
        Story story = Story
                .builder()
                .id(UUID.randomUUID())
                .title("title")
                .subtitle("subtitle")
                .text("text")
                .author("author")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .name("name")
                        .background("background")
                        .build()))
                .build();

        Mockito.when(storyService.createStory(createStory))
                .thenReturn(story);

        Story storyResponse = storyOperation.saveStory(createStory);

        Mockito.verify(storyService).createStory(createStory);

        Assertions.assertNotNull(storyResponse);
        Assertions.assertEquals(story.getId(), storyResponse.getId());
        Assertions.assertEquals(story.getTitle(), storyResponse.getTitle());
        Assertions.assertEquals(story.getSubtitle(), storyResponse.getSubtitle());
        Assertions.assertEquals(story.getText(), storyResponse.getText());
        Assertions.assertEquals(story.getAuthor(), storyResponse.getAuthor());
        Assertions.assertIterableEquals(story.getCategories(), storyResponse.getCategories());
    }

    @Test
    @DisplayName("Retrieve all stories")
    void retrieveAllStories_ok() {

        List<Story> stories = List.of(Story
                .builder()
                .id(UUID.randomUUID())
                .title("title")
                .subtitle("subtitle")
                .text("text")
                .author("author")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .name("name")
                        .background("background")
                        .build()))
                .build());

        Mockito.when(storyService.findAll())
                .thenReturn(stories);

        List<Story> storiesResponse = storyOperation.retrieveStories();

        Mockito.verify(storyService).findAll();

        Assertions.assertNotNull(storiesResponse);
        Assertions.assertEquals(1, storiesResponse.size());
        storiesResponse
                .stream()
                .findFirst()
                .ifPresent(storyResponse -> {
                    Assertions.assertNotNull(storyResponse);
                    Assertions.assertEquals(stories.get(0).getId(), storyResponse.getId());
                    Assertions.assertEquals(stories.get(0).getTitle(), storyResponse.getTitle());
                    Assertions.assertEquals(stories.get(0).getSubtitle(), storyResponse.getSubtitle());
                    Assertions.assertEquals(stories.get(0).getText(), storyResponse.getText());
                    Assertions.assertEquals(stories.get(0).getAuthor(), storyResponse.getAuthor());
                    Assertions.assertIterableEquals(stories.get(0).getCategories(), storyResponse.getCategories());
                });
    }

    @Test
    @DisplayName("Given id retrieve story")
    void givenId_retrieveStory_ok() {

        UUID id = UUID.randomUUID();
        Story story = Story
                .builder()
                .id(id)
                .title("title")
                .subtitle("subtitle")
                .text("text")
                .author("author")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .name("name")
                        .background("background")
                        .build()))
                .build();

        Mockito.when(storyService.findById(id))
                .thenReturn(story);

        Story storyResponse = storyOperation.retrieveStory(id);

        Mockito.verify(storyService).findById(id);

        Assertions.assertNotNull(storyResponse);
        Assertions.assertEquals(story.getId(), storyResponse.getId());
        Assertions.assertEquals(story.getTitle(), storyResponse.getTitle());
        Assertions.assertEquals(story.getSubtitle(), storyResponse.getSubtitle());
        Assertions.assertEquals(story.getText(), storyResponse.getText());
        Assertions.assertEquals(story.getAuthor(), storyResponse.getAuthor());
        Assertions.assertIterableEquals(story.getCategories(), storyResponse.getCategories());
    }

    @Test
    @DisplayName("Given updateStory object update story successfully")
    void givenUpdateStory_updateStory_ok() {
        UUID id = UUID.randomUUID();
        UpdateStory updateStory = UpdateStory
                .builder()
                .id(id)
                .title("title")
                .subtitle("subtitle")
                .text("text")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .name("name")
                        .background("background")
                        .build()))
                .build();
        Story story = Story
                .builder()
                .id(id)
                .title("title")
                .subtitle("subtitle")
                .text("text")
                .author("author")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .name("name")
                        .background("background")
                        .build()))
                .build();

        Mockito.when(storyService.updateStory(updateStory))
                .thenReturn(story);

        Story storyResponse = storyOperation.updateStory(updateStory);

        Mockito.verify(storyService).updateStory(updateStory);


        Assertions.assertNotNull(storyResponse);
        Assertions.assertEquals(story.getId(), storyResponse.getId());
        Assertions.assertEquals(story.getTitle(), storyResponse.getTitle());
        Assertions.assertEquals(story.getSubtitle(), storyResponse.getSubtitle());
        Assertions.assertEquals(story.getText(), storyResponse.getText());
        Assertions.assertEquals(story.getAuthor(), storyResponse.getAuthor());
        Assertions.assertIterableEquals(story.getCategories(), storyResponse.getCategories());
    }


    @Test
    @DisplayName("Given id delete category")
    void givenId_deleteCategory_ok() {

        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(storyService).deleteById(id);

        storyOperation.deleteStory(id);

        Mockito.verify(storyService).deleteById(id);
    }
}
