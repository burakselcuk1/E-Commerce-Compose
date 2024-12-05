package com.example.e_commerce_compose.screens.categoryScreen

import androidx.lifecycle.viewModelScope
import com.example.core.BaseViewModel
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryWithSubCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoriesUseCase
) : BaseViewModel() {

    private val _categories = MutableStateFlow<List<CategoryWithSubCategories>>(emptyList())
    val categories: StateFlow<List<CategoryWithSubCategories>> = _categories.asStateFlow()

    private val _expandedCategoryId = MutableStateFlow<Int?>(null)
    val expandedCategoryId: StateFlow<Int?> = _expandedCategoryId.asStateFlow()

    fun onCategoryClick(categoryId: Int) {
        _expandedCategoryId.value =
            if (_expandedCategoryId.value == categoryId) null else categoryId
    }

    fun fetchCategories() {
        launchWithLoading(
            scope = viewModelScope,
            block = { categoryUseCase() },
            onSuccess = { fetchedCategories ->
                _categories.value = fetchedCategories
            },
            onError = {
                _categories.value = emptyList()
            }
        )
    }
}