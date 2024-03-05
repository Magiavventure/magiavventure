package it.magiavventure.mapper;

import it.magiavventure.mongo.entity.ECategory;
import it.magiavventure.mongo.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category map(ECategory eCategory);
}
