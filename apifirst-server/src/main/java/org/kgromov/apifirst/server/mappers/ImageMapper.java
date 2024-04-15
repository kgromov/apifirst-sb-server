package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.ImageDto;
import org.kgromov.apifirst.model.ProductImageUpdateDto;
import org.kgromov.apifirst.server.domain.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ImageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    Image dtoToImage(ImageDto imageDto);

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.created", target = "modified")
    ImageDto imageToDto(Image image);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    void updateImage(ProductImageUpdateDto image, @MappingTarget Image target);
}
