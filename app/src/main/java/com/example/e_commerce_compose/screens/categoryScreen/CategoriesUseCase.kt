package com.example.e_commerce_compose.screens.categoryScreen

import com.example.e_commerce_compose.network.model.response.Category
import com.example.e_commerce_compose.repository.CategoryRepository

class CategoriesUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategories()
    }
}
