package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.ImageDto;
import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.server.domain.Product;
import org.kgromov.apifirst.server.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class ProductMapperDecorator implements ProductMapper {
    @Autowired
    @Qualifier("delegate")
    private ProductMapper productMapper;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ImageMapper imageMapper;

    @Override
    public Product dtoToProduct(ProductDto productDto) {
        Product product = productMapper.dtoToProduct(productDto);
        this.mapDtoToImages(productDto.getImages(), product);
        return product;
    }

    @Override
    public Product dtoToProduct(ProductCreateDto createDto) {
        Product product = productMapper.dtoToProduct(createDto);
        product.setCategories(categoryRepository.findAllByCodeIn(createDto.getCategories()));
        this.mapDtoToImages(createDto.getImages(), product);
        return product;
    }

    private void mapDtoToImages(List<ImageDto> imageDtos, Product product) {
        if (!CollectionUtils.isEmpty(imageDtos)) {
            var images = imageDtos.stream().map(imageMapper::dtoToImage).toList();
            product.setImages(images);
        }
    }

    @Override
    public ProductDto productToDto(Product product) {
        ProductDto productDto = productMapper.productToDto(product);
        if (!CollectionUtils.isEmpty(product.getImages())) {
            var images = product.getImages().stream().map(imageMapper::imageToDto).toList();
            productDto.setImages(images);
        }
        return productDto;
    }
}
