package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.server.domain.Product;
import org.kgromov.apifirst.server.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class ProductMapperDecorator implements ProductMapper {
    @Autowired
    @Qualifier("delegate")
    private ProductMapper productMapper;
    @Autowired private CategoryRepository categoryRepository;

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
