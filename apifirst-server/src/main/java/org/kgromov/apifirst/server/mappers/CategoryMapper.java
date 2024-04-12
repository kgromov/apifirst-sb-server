package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.CategoryDto;
import org.kgromov.apifirst.server.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CategoryMapper {

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.modified", target = "modified")
    CategoryDto categoryToDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category dtoToCategory(CategoryDto categoryDto);
}
