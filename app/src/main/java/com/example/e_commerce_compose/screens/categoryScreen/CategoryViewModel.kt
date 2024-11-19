package com.example.e_commerce_compose.screens.categoryScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.network.model.response.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: CategoriesUseCase
) : ViewModel() {

    private val _categories = mutableStateOf<List<Category>>(emptyList())
    val categories: State<List<Category>> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val data = getCategoriesUseCase()
                _categories.value = data
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching products", e)
                val errorBody = e.message
                Log.e("GraphQL Error", "HTTP 400 Error: $errorBody")
            }
        }
    }
}
