package com.example.e_commerce_compose.screens.categoryScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryWithSubCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoriesUseCase
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
                _categories.value = categoryUseCase()
            } catch (e: Exception) {
                _categories.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

