package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.ImageDto;
import org.kgromov.apifirst.server.domain.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ImageMapper {
    /*@Mapping(target = "title", expression = "java(signUpUser.getTitle().toString())")
    public SignUpUserDto signUpUserToSignUpUserDto(SignUpUser signUpUser);
    @Mapping(target = "title", source = "java(Title.valueOf(signUpUserDto.getTitle().toUpperCase()))")
    public SignUpUser signUpUserDtoToSignUpUser(SignUpUserDto signUpUserDto);*/
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "uri", expression = "java(imageDto.getUri().toString())")
    Image dtoToImage(ImageDto imageDto);

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.created", target = "modified")
    @Mapping(target = "uri", expression = "java(java.net.URI.create(image.getUri()))")
    ImageDto imageToDto(Image image);
}
