package com.personal.tejiendoarte.service.admin.category;

import com.personal.tejiendoarte.dto.CategoryDto;
import com.personal.tejiendoarte.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();
    Category createCategory(CategoryDto categoryDto);

}
