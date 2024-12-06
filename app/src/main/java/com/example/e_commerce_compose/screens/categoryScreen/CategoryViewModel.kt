package com.example.e_commerce_compose.screens.categoryScreen

import androidx.lifecycle.viewModelScope
import com.example.core.BaseViewModel
import com.example.core.LoadingManager
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryUiState
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryWithSubCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoriesUseCase,
    loadingManager: LoadingManager
) : BaseViewModel(loadingManager) {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun onCategoryClick(categoryId: Int) {
        _uiState.value = _uiState.value.copy(
            expandedCategoryId = if (_uiState.value.expandedCategoryId == categoryId) null else categoryId
        )
    }

    fun fetchCategories() {
        launchWithLoading(
            scope = viewModelScope,
            block = { categoryUseCase() },
            onSuccess = { fetchedCategories ->
                _uiState.value = _uiState.value.copy(
                    categories = fetchedCategories,
                )
            },
            onError = { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = exception.message,
                )
            }
        )
    }
}