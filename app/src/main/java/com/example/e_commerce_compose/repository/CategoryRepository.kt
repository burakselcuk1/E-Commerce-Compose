package com.example.e_commerce_compose.repository

import com.example.e_commerce_compose.network.model.response.Category
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryWithSubCategories

interface CategoryRepository {
    suspend fun fetchCategories(): List<CategoryWithSubCategories>
}