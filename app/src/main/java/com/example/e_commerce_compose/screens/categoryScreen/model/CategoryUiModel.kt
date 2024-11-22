package com.example.e_commerce_compose.screens.categoryScreen.model

data class CategoryWithSubCategories(
    val category: CategoryUiModel,
    val subcategories: List<SubCategory>
)

data class SubCategory(
    val list: CategoryUiModel,
    val items: List<CategoryUiModel>
)

data class CategoryUiModel(
    val id: Int,
    val name: String,
    val icon: String,
    val menuId: Int,
    val parentMenuId: Int?,
    val type: String
)