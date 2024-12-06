package com.example.e_commerce_compose.screens.productScreen

import androidx.lifecycle.viewModelScope
import com.example.core.BaseViewModel
import com.example.core.LoadingManager
import com.example.e_commerce_compose.screens.productScreen.model.ProductUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val fetchProductsUseCase: ProductsUseCase,
    loadingManager: LoadingManager
) : BaseViewModel(loadingManager) {

    private val _products = MutableStateFlow<List<ProductUiModel>>(emptyList())
    val products: StateFlow<List<ProductUiModel>> = _products.asStateFlow()

    private var currentPage = 1
    private var canLoadMore = true

    fun fetchProducts(categoryId: String, isInitialLoad: Boolean = true) {
        if (isInitialLoad) {
            currentPage = 1
            _products.value = emptyList()
            canLoadMore = true
        }

        if (!canLoadMore || isLoading.value) return

        launchWithLoading(
            scope = viewModelScope,
            block = {
                fetchProductsUseCase(categoryId, currentPage)
            },
            onSuccess = { newProducts ->
                if (newProducts.isEmpty()) {
                    canLoadMore = false
                } else {
                    _products.value = if (isInitialLoad) {
                        newProducts
                    } else {
                        _products.value + newProducts
                    }
                    currentPage++
                }
            },
            onError = { e ->
                if (isInitialLoad) {
                    _products.value = emptyList()
                }
            }
        )
    }
}
