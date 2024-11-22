package com.example.e_commerce_compose.screens.categoryScreen

import com.example.e_commerce_compose.repository.CategoryRepository
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryWithSubCategories
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<CategoryWithSubCategories> {
        return repository.fetchCategories()
    }
}