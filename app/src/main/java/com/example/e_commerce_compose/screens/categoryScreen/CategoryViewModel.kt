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

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val apolloClient: ApolloClient
) : ViewModel() {
    private val _categories = MutableStateFlow<List<MenuCategory>>(emptyList())
    val categories: StateFlow<List<MenuCategory>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apolloClient.query(GetCategoriesQuery()).execute()
                val menuItems = response.data?.menuByType?.mapNotNull { item ->
                    MenuCategory(
                        id = (item?.id ?: "").toString(),
                        name = item?.name ?: "",
                        icon = item?.icon ?: "",
                        menuId = (item?.menuId ?: "").toString(),
                        parentMenuId = (item?.parentMenuId ?: "").toString(),
                        type = item?.type ?: ""
                    )
                } ?: emptyList()
                _categories.value = menuItems
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories", e)
                _categories.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

// Data class for categories
data class MenuCategory(
    val id: String,
    val name: String,
    val icon: String,
    val menuId: String,
    val parentMenuId: String,
    val type: String
)