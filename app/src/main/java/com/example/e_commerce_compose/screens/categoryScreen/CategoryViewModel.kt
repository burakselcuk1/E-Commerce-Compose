package com.example.e_commerce_compose.screens.categoryScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.e_commerce_compose.network.model.response.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.GetCategoriesQuery
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val apolloClient: ApolloClient
) : ViewModel() {
    private val _categories = MutableStateFlow<List<CategoryWithSubCategories>>(emptyList())
    val categories: StateFlow<List<CategoryWithSubCategories>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _expandedCategoryId = MutableStateFlow<Int?>(null)
    val expandedCategoryId: StateFlow<Int?> = _expandedCategoryId.asStateFlow()

    fun onCategoryClick(categoryId: Int) {
        _expandedCategoryId.value = if (_expandedCategoryId.value == categoryId) null else categoryId
    }

    fun fetchCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apolloClient.query(GetCategoriesQuery()).execute()
                val menuItems = response.data?.menuByType?.filterNotNull() ?: emptyList()

                val mainCategories = menuItems.filter {
                    it.parentMenuId == null && it.type != "NoSubCategory"
                }

                val categoriesWithSubs = mainCategories.map { mainCategory ->
                    val subLists = menuItems.filter {
                        it.parentMenuId == mainCategory.id && it.type == "List"
                    }

                    val subCategories = subLists.map { list ->
                        SubCategory(
                            list = MenuCategory(
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
                                MenuCategory(
                                    id = item.id,
                                    name = item.name ?: "",
                                    icon = item.icon ?: "",
                                    menuId = item.menuId ?: 0,
                                    parentMenuId = item.parentMenuId,
                                    type = item.type ?: ""
                                )
                            }
                        )
                    }

                    CategoryWithSubCategories(
                        category = MenuCategory(
                            id = mainCategory.id,
                            name = mainCategory.name ?: "",
                            icon = mainCategory.icon ?: "",
                            menuId = mainCategory.menuId ?: 0,
                            parentMenuId = mainCategory.parentMenuId,
                            type = mainCategory.type ?: ""
                        ),
                        subcategories = subCategories
                    )
                }

                _categories.value = categoriesWithSubs
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories", e)
                _categories.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class CategoryWithSubCategories(
    val category: MenuCategory,
    val subcategories: List<SubCategory>
)

data class SubCategory(
    val list: MenuCategory,
    val items: List<MenuCategory>
)

data class MenuCategory(
    val id: Int,
    val name: String,
    val icon: String,
    val menuId: Int,
    val parentMenuId: Int?,
    val type: String
)