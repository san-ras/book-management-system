package com.example.bookmanagementsystem.services;

import com.example.bookmanagementsystem.dto.CategoryDTO;
import com.example.bookmanagementsystem.entities.Category;
import com.example.bookmanagementsystem.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDTO).toList();
    }

    public CategoryDTO addCategory(CategoryDTO newCategoryDTO) {
        if (newCategoryDTO.getName() == null || newCategoryDTO.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        } else if (categoryRepository.findByName(newCategoryDTO.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This category already exists");
        }
        Category category = categoryRepository.save(convertToEntity(newCategoryDTO));

        return convertToDTO(category);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setCategoryId(categoryDTO.getCategoryId());

        return category;
    }

    public void deleteCategory(Integer id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with specified id does not exist"));
        categoryRepository.delete(categoryToDelete);
    }

    public CategoryDTO updateCategory(CategoryDTO newCategoryDTO, Integer id) {
        if (newCategoryDTO.getName() == null || newCategoryDTO.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }
            Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with specified id does not exist"));
            category.setName(newCategoryDTO.getName());
            categoryRepository.save(category);
            return convertToDTO(category);
    }
}
