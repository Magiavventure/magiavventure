package it.magiavventure.model.category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateCategory {

    @NotNull
    private String name;
    @NotNull
    private String background;
    private boolean active;

}
