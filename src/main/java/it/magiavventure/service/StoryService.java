package it.magiavventure.service;

import it.magiavventure.common.error.MagiavventureException;
import it.magiavventure.error.MessageCode;
import it.magiavventure.jwt.config.AppContext;
import it.magiavventure.jwt.service.OwnershipService;
import it.magiavventure.mapper.StoryMapper;
import it.magiavventure.model.story.CreateStory;
import it.magiavventure.model.story.UpdateStory;
import it.magiavventure.mongo.entity.EStory;
import it.magiavventure.mongo.entity.EUser;
import it.magiavventure.mongo.model.Story;
import it.magiavventure.mongo.repository.StoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StoryService {

    private final StoryService self;
    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    private final OwnershipService ownershipService;
    private final AppContext appContext;

    @CacheEvict(value = {"stories"}, key = "'all'")
    public Story createStory(CreateStory createStory) {
        EUser user = appContext.getUser();
        EStory storyToSave = EStory
                .builder()
                .id(UUID.randomUUID())
                .title(createStory.getTitle())
                .subtitle(createStory.getSubtitle())
                .text(createStory.getText())
                .author(user.getName())
                .categories(createStory.getCategories())
                .active(false)
                .verified(false)
                .build();
        EStory savedStory = storyRepository.save(storyToSave);
        return storyMapper.map(savedStory);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = {"story"}, key = "#p0.id"),
                    @CacheEvict(value = {"stories"}, key = "'all'")
            }
    )
    public Story updateStory(UpdateStory updateStory) {
        EStory storyToUpdate = self.findEntityById(updateStory.getId());
        validateAuthor(storyToUpdate.getAuthor());
        storyToUpdate.setTitle(updateStory.getTitle());
        storyToUpdate.setSubtitle(updateStory.getSubtitle());
        storyToUpdate.setText(updateStory.getText());
        storyToUpdate.setCategories(updateStory.getCategories());
        storyToUpdate.setActive(false);
        storyToUpdate.setVerified(false);
        EStory updatedStory = storyRepository.save(storyToUpdate);
        return storyMapper.map(updatedStory);
    }

    public Story findById(UUID id) {
        return storyMapper.map(self.findEntityById(id));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = {"story"}, key = "#p0"),
                    @CacheEvict(value = {"stories"}, key = "'all'")
            }
    )
    public void deleteById(UUID id) {
        EStory eStory = self.findEntityById(id);
        validateAuthor(eStory.getAuthor());
        storyRepository.deleteById(id);
    }

    @Cacheable(value = "story", key = "#p0")
    public EStory findEntityById(UUID id) {
        return storyRepository.findById(id)
                .orElseThrow(() -> MagiavventureException.of(MessageCode.STORY_NOT_FOUND, id.toString()));
    }

    @Cacheable(value = "stories", key = "'all'")
    public List<Story> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return storyRepository.findAll(sort)
                .stream()
                .map(storyMapper::mapPartial)
                .toList();
    }

    public void validateAuthor(String author) {
        ownershipService.validateOwnership(author);
    }
}
