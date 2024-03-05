package it.magiavventure.service;

import it.magiavventure.common.error.MagiavventureException;
import it.magiavventure.jwt.config.AppContext;
import it.magiavventure.jwt.service.OwnershipService;
import it.magiavventure.mapper.StoryMapper;
import it.magiavventure.model.story.CreateStory;
import it.magiavventure.model.story.UpdateStory;
import it.magiavventure.mongo.entity.EStory;
import it.magiavventure.mongo.entity.EUser;
import it.magiavventure.mongo.model.Category;
import it.magiavventure.mongo.model.Story;
import it.magiavventure.mongo.repository.StoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story service tests")
class StoryServiceTest {
    @InjectMocks
    private StoryService storyService;
    @Mock
    private StoryService self;
    @Spy
    private StoryMapper storyMapper = Mappers.getMapper(StoryMapper.class);
    @Mock
    private StoryRepository storyRepository;
    @Mock
    private OwnershipService ownershipService;
    @Mock
    private AppContext appContext;
    @Captor
    private ArgumentCaptor<EStory> eStoryArgumentCaptor;
    @Captor
    private ArgumentCaptor<Sort> sortArgumentCaptor;

    @Test
    @DisplayName("Given createStory object create a new story")
    void givenCreateStory_createANewStory_ok() {
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
        EStory eStory = EStory
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

        Mockito.when(appContext.getUser())
                .thenReturn(EUser.builder().name("name").build());
        Mockito.when(storyRepository.save(eStoryArgumentCaptor.capture()))
                .thenReturn(eStory);

        Story story = storyService.createStory(createStory);

        Mockito.verify(appContext).getUser();
        Mockito.verify(storyRepository).save(eStoryArgumentCaptor.capture());


        EStory storyCapt = eStoryArgumentCaptor.getValue();
        Assertions.assertNotNull(storyCapt);
        Assertions.assertNotNull(storyCapt.getId());
        Assertions.assertEquals(createStory.getTitle(), storyCapt.getTitle());
        Assertions.assertEquals(createStory.getSubtitle(), storyCapt.getSubtitle());
        Assertions.assertEquals(createStory.getText(), storyCapt.getText());
        Assertions.assertEquals("name", storyCapt.getAuthor());
        Assertions.assertIterableEquals(createStory.getCategories(), storyCapt.getCategories());
        Assertions.assertFalse(storyCapt.isActive());
        Assertions.assertFalse(storyCapt.isVerified());
        Assertions.assertNotNull(story);
        Assertions.assertEquals(eStory.getId(), story.getId());
        Assertions.assertEquals(eStory.getTitle(), story.getTitle());
        Assertions.assertEquals(eStory.getSubtitle(), story.getSubtitle());
        Assertions.assertEquals(eStory.getText(), story.getText());
        Assertions.assertEquals(eStory.getAuthor(), story.getAuthor());
        Assertions.assertIterableEquals(eStory.getCategories(), story.getCategories());
    }

    @Test
    @DisplayName("Given updateStory object update a story")
    void givenUpdateStory_updateStory_ok() {
        UUID id = UUID.randomUUID();
        UpdateStory updateStory = UpdateStory
                .builder()
                .id(id)
                .title("title 2")
                .subtitle("subtitle 2")
                .text("text 2")
                .categories(List.of(Category
                        .builder()
                        .id(UUID.randomUUID())
                        .name("name 2")
                        .background("background 2")
                        .build()))
                .build();
        EStory eStory = EStory
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

        Mockito.when(self.findEntityById(id))
                .thenReturn(eStory);
        Mockito.doNothing().when(ownershipService).validateOwnership(eStory.getAuthor());
        Mockito.when(storyRepository.save(eStoryArgumentCaptor.capture()))
                .thenReturn(eStory);

        Story story = storyService.updateStory(updateStory);

        Mockito.verify(self).findEntityById(id);
        Mockito.verify(ownershipService).validateOwnership(eStory.getAuthor());
        Mockito.verify(storyRepository).save(eStoryArgumentCaptor.capture());


        EStory storyCapt = eStoryArgumentCaptor.getValue();
        Assertions.assertNotNull(storyCapt);
        Assertions.assertNotNull(storyCapt.getId());
        Assertions.assertEquals(updateStory.getTitle(), storyCapt.getTitle());
        Assertions.assertEquals(updateStory.getSubtitle(), storyCapt.getSubtitle());
        Assertions.assertEquals(updateStory.getText(), storyCapt.getText());
        Assertions.assertEquals(eStory.getAuthor(), storyCapt.getAuthor());
        Assertions.assertIterableEquals(updateStory.getCategories(), storyCapt.getCategories());
        Assertions.assertFalse(storyCapt.isActive());
        Assertions.assertFalse(storyCapt.isVerified());
        Assertions.assertNotNull(story);
        Assertions.assertEquals(updateStory.getId(), story.getId());
        Assertions.assertEquals(updateStory.getTitle(), story.getTitle());
        Assertions.assertEquals(updateStory.getSubtitle(), story.getSubtitle());
        Assertions.assertEquals(updateStory.getText(), story.getText());
        Assertions.assertEquals(eStory.getAuthor(), story.getAuthor());
        Assertions.assertIterableEquals(updateStory.getCategories(), story.getCategories());
    }

    @Test
    @DisplayName("Given id find story")
    void givenId_findStory_ok() {
        UUID id = UUID.randomUUID();
        EStory eStory = EStory
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

        Mockito.when(self.findEntityById(id))
                .thenReturn(eStory);

        Story story = storyService.findById(id);

        Mockito.verify(self).findEntityById(id);

        Assertions.assertNotNull(story);
        Assertions.assertEquals(eStory.getId(), story.getId());
        Assertions.assertEquals(eStory.getTitle(), story.getTitle());
        Assertions.assertEquals(eStory.getSubtitle(), story.getSubtitle());
        Assertions.assertEquals(eStory.getText(), story.getText());
        Assertions.assertEquals(eStory.getAuthor(), story.getAuthor());
        Assertions.assertIterableEquals(eStory.getCategories(), story.getCategories());
    }

    @Test
    @DisplayName("Given id delete story")
    void givenId_deleteStory_ok() {
        UUID id = UUID.randomUUID();
        EStory eStory = EStory
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

        Mockito.when(self.findEntityById(id))
                .thenReturn(eStory);
        Mockito.doNothing().when(ownershipService).validateOwnership(eStory.getAuthor());

        storyService.deleteById(id);

        Mockito.verify(self).findEntityById(id);
        Mockito.verify(ownershipService).validateOwnership(eStory.getAuthor());
    }

    @Test
    @DisplayName("Given id find entity story")
    void givenId_findEntityStory_ok() {
        UUID id = UUID.randomUUID();
        EStory eStory = EStory
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

        Mockito.when(storyRepository.findById(id))
                .thenReturn(Optional.of(eStory));

        EStory story = storyService.findEntityById(id);

        Mockito.verify(storyRepository).findById(id);

        Assertions.assertNotNull(story);
        Assertions.assertEquals(eStory, story);
    }

    @Test
    @DisplayName("Given id find entity story but not found and throw exception")
    void givenId_findEntityStory_throwException() {
        UUID id = UUID.randomUUID();

        Mockito.when(storyRepository.findById(id))
                .thenReturn(Optional.empty());

        MagiavventureException exception = Assertions.assertThrows(MagiavventureException.class,
                () -> storyService.findEntityById(id));

        Mockito.verify(storyRepository).findById(id);

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("story-not-found", exception.getError().getKey());
        Assertions.assertIterableEquals(List.of(id.toString()), Arrays.asList(exception.getError().getArgs()));
    }

    @Test
    @DisplayName("Find all stories sorting them")
    void findAllStories_sortingThem_ok() {
        UUID id = UUID.randomUUID();
        List<EStory> eStories = List.of(EStory
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
                .build());

        Mockito.when(storyRepository.findAll(sortArgumentCaptor.capture()))
                .thenReturn(eStories);

        List<Story> stories = storyService.findAll();

        Mockito.verify(storyRepository).findAll(sortArgumentCaptor.capture());

        Assertions.assertNotNull(stories);
        stories.stream().findFirst().ifPresent(story -> {
            Assertions.assertEquals(eStories.get(0).getId(), story.getId());
            Assertions.assertEquals(eStories.get(0).getTitle(), story.getTitle());
            Assertions.assertEquals(eStories.get(0).getSubtitle(), story.getSubtitle());
            Assertions.assertEquals(eStories.get(0).getText(), story.getText());
            Assertions.assertEquals(eStories.get(0).getAuthor(), story.getAuthor());
            Assertions.assertIterableEquals(eStories.get(0).getCategories(), story.getCategories());
        });
    }
}
