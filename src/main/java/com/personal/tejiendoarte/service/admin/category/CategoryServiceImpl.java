package com.personal.tejiendoarte.service.admin.category;

import com.personal.tejiendoarte.dto.CategoryDto;
import com.personal.tejiendoarte.entity.Category;
import com.personal.tejiendoarte.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

}
