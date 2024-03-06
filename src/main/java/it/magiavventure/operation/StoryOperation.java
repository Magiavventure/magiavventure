package it.magiavventure.operation;

import it.magiavventure.model.story.CreateStory;
import it.magiavventure.model.story.UpdateStory;
import it.magiavventure.mongo.model.Story;
import it.magiavventure.service.StoryService;
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
@RequestMapping("/story/v1")
public class StoryOperation {

    private final StoryService storyService;

    @PostMapping("/saveStory")
    @ResponseStatus(HttpStatus.CREATED)
    public Story saveStory(@RequestBody @Valid CreateStory createStory) {
        return storyService.createStory(createStory);
    }

    @GetMapping("/retrieveStories")
    public List<Story> retrieveStories() {
        return storyService.findAll();
    }

    @GetMapping("/retrieveStory/{id}")
    public Story retrieveStory(@PathVariable(name = "id") UUID id) {
        return storyService.findById(id);
    }

    @PutMapping("/updateStory")
    public Story updateStory(@RequestBody @Valid UpdateStory updateStory) {
        return storyService.updateStory(updateStory);
    }

    @DeleteMapping("/deleteStory/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStory(@PathVariable(name = "id") UUID id) {
        storyService.deleteById(id);
    }
}
