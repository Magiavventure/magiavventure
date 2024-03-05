package it.magiavventure.model.story;

import it.magiavventure.mongo.model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UpdateStory {

    @NotNull
    private UUID id;
    @NotNull
    private String title;
    private String subtitle;
    @NotNull
    private String text;
    @NotEmpty
    private List<Category> categories;
}
