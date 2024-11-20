package com.example.e_commerce_compose.screens.mainScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.network.model.response.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: MainScreenUseCase
) : ViewModel() {

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    init {
        fetchProducts("1.0")
    }

    private fun fetchProducts(categoryId: String) {
        viewModelScope.launch {
            try {
                val data = getProductsByCategoryUseCase(categoryId)
                _products.value = data
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products", e)
                val errorBody = e.message
                Log.e("GraphQL Error", "HTTP 400 Error: $errorBody")
            }
        }
    }
}
