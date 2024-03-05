package it.magiavventure.mapper;

import it.magiavventure.mongo.entity.EStory;
import it.magiavventure.mongo.model.Story;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StoryMapper {
    @Mapping(target = "readTime", expression = "java(getReadTime(eStory.getText()))")
    public abstract Story map(EStory eStory);

    @Mapping(target = "readTime", expression = "java(getReadTime(eStory.getText()))")
    @Mapping(target = "text", qualifiedByName = "limitText")
    public abstract Story mapPartial(EStory eStory);

    @Named("limitText")
    protected String limitText(String text) {
        return text.length()>100 ? text.substring(0,100) + "..." : text;
    }
    protected int getReadTime(String text) {
        float words = text.split("\\s").length;
        return Math.round((words/200)*60);
    }
}
