package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.CategoryDto;
import org.kgromov.apifirst.server.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.kgromov.apifirst.server.controllers.CategoryController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class CategoryController {
    static final String BASE_URL = "/v1/categories";

    private final CategoryService categoryService;

    @GetMapping
    ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
