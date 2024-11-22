package com.example.e_commerce_compose.screens.categoryScreen

import com.example.GetCategoriesQuery
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryUiModel
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryWithSubCategories
import com.example.e_commerce_compose.screens.categoryScreen.model.SubCategory

class CategoryUiMapper {
    fun mapToCategories(menuItems: List<GetCategoriesQuery.MenuByType>): List<CategoryWithSubCategories> {
        val mainCategories = menuItems.filter {
            it.parentMenuId == null && it.type != "NoSubCategory"
        }

        return mainCategories.map { mainCategory ->
            val subLists = menuItems.filter {
                it.parentMenuId == mainCategory.id && it.type == "List"
            }

            val subCategories = subLists.map { list ->
                SubCategory(
                    list = CategoryUiModel(
                        id = list.id,
                        name = list.name ?: "",
                        icon = list.icon ?: "",
                        menuId = list.menuId ?: 0,
                        parentMenuId = list.parentMenuId,
                        type = list.type ?: ""
                    ),
                    items = menuItems.filter {
                        it.parentMenuId == list.id
                    }.map { item ->
                        mapToMenuCategory(item)
                    }
                )
            }

            CategoryWithSubCategories(
                category = mapToMenuCategory(mainCategory),
                subcategories = subCategories
            )
        }
    }

    private fun mapToMenuCategory(item: GetCategoriesQuery.MenuByType): CategoryUiModel {
        return CategoryUiModel(
            id = item.id,
            name = item.name ?: "",
            icon = item.icon ?: "",
            menuId = item.menuId ?: 0,
            parentMenuId = item.parentMenuId,
            type = item.type ?: ""
        )
    }
}