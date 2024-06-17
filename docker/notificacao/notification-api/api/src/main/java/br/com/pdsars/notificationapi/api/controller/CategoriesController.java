package br.com.pdsars.notificationapi.api.controller;

import br.com.pdsars.notificationapi.api.Constants;
import br.com.pdsars.notificationapi.api.dto.CategoryDTO;
import br.com.pdsars.notificationapi.api.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoriesController {
    private final CategoryService categoryService;

    public CategoriesController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<CategoryDTO> listCategories() {
        return this.categoryService.listCategories(Constants.MOCKED_CLIENT_ID);
    }

}
