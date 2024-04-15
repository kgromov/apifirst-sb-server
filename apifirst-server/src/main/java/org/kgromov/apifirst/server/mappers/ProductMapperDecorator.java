package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.model.ProductUpdateDto;
import org.kgromov.apifirst.server.domain.Category;
import org.kgromov.apifirst.server.domain.Product;
import org.kgromov.apifirst.server.repositories.CategoryRepository;
import org.kgromov.apifirst.server.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Objects;

public abstract class ProductMapperDecorator implements ProductMapper {
    @Autowired
    @Qualifier("delegate")
    private ProductMapper productMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageMapper imageMapper;

    @Override
    public ProductUpdateDto productToUpdateDto(Product product) {
        ProductUpdateDto productUpdateDto = productMapper.productToUpdateDto(product);
        if (product.getCategories() != null) {
            var categoryCodes = product.getCategories().stream().map(Category::getCode).toList();
            productUpdateDto.setCategories(categoryCodes);
        }
        return productUpdateDto;
    }

    @Override
    public Product dtoToProduct(ProductUpdateDto productUpdateDto) {
        Product product = productMapper.dtoToProduct(productUpdateDto);
        var categories = categoryRepository.findAllByCodeIn(productUpdateDto.getCategories());
        product.setCategories(categories);
        product.setImages(new ArrayList<>());
        this.updateImages(productUpdateDto, product);
        return product;
    }

    @Override
    public void updateProduct(ProductUpdateDto productUpdateDto, Product product) {
        productMapper.updateProduct(productUpdateDto, product);
        this.updateImages(productUpdateDto, product);
        var categories = categoryRepository.findAllByCodeIn(productUpdateDto.getCategories());
        product.setCategories(categories);
    }

    private void updateImages(ProductUpdateDto productUpdateDto, Product product) {
        if (productUpdateDto.getImages() != null) {
            productUpdateDto.getImages().stream()
                    .filter(imageDto -> Objects.nonNull(imageDto.getId()))
                    .forEach(imageDto -> imageRepository.findById(imageDto.getId())
                            .ifPresent(image -> {
                                imageMapper.updateImage(imageDto, image);
                                product.getImages().add(image);
                            }));
        }
    }

    @Override
    public Product dtoToProduct(ProductDto productDto) {
        return productMapper.dtoToProduct(productDto);
    }

    @Override
    public Product dtoToProduct(ProductCreateDto createDto) {
        Product product = productMapper.dtoToProduct(createDto);
        product.setCategories(categoryRepository.findAllByCodeIn(createDto.getCategories()));
        return product;
    }

    @Override
    public ProductDto productToDto(Product product) {
        return productMapper.productToDto(product);
    }
}
