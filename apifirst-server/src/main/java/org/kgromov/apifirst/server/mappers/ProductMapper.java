package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.server.domain.Product;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product  dtoToProduct(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product dtoToProduct(ProductCreateDto productCreateDto);

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.modified", target = "modified")
    @Mapping(target = "images", ignore = true)
    ProductDto productToDto(Product product);
}