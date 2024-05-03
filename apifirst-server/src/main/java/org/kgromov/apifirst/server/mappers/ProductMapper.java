package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.model.ProductPatchDto;
import org.kgromov.apifirst.model.ProductUpdateDto;
import org.kgromov.apifirst.server.domain.Product;
import org.mapstruct.*;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {


    @Mapping(target = "categories", ignore = true)
    ProductUpdateDto productToUpdateDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Product dtoToProduct(ProductUpdateDto productUpdateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateProduct(ProductUpdateDto product, @MappingTarget Product target);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Product dtoToProduct(ProductCreateDto productCreateDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    Product dtoToProduct(ProductDto productDto);

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.modified", target = "modified")
    ProductDto productToDto(Product product);


    @Mapping(target = "categories", ignore = true)
    ProductPatchDto productToPatchDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "images", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void patchProduct(ProductPatchDto productPatchDto, @MappingTarget Product target);
}