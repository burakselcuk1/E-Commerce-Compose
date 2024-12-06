package com.example.e_commerce_compose.screens.categoryScreen.model

data class CategoryUiState(
    val categories: List<CategoryWithSubCategories>? = null,
    val expandedCategoryId: Int? = null,
    val errorMessage: String? = null
)