package com.example.e_commerce_compose.screens.productScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.CategoryV2Query
import com.example.type.PageSizeOptionTypeInput
import com.example.type.PriceRangeFilterModelTypeInput
import com.example.type.ProductSortingItemTypeInput
import com.example.type.SpecificationFilterTypeInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.type.CatalogPagingFilteringTypeInput

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val apolloClient: ApolloClient
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchProducts(categoryId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Apollo tarafından generate edilen CatalogPagingFilteringTypeInput kullanılıyor
                val catalogPagingInput = CatalogPagingFilteringTypeInput(
                    pageNumber = Optional.present(1),
                    pageSize = Optional.present(10),
                    orderBy = Optional.absent(),
                    allowProductSorting = Optional.present(true),
                    allowCustomersToSelectPageSize = Optional.present(false)
                )

                val response = apolloClient.query(
                    CategoryV2Query(
                        categoryId = Optional.present(categoryId),
                        command = Optional.present(catalogPagingInput)
                    )
                ).execute()

                val productData = response.data?.categoryV2?.data?.products ?: emptyList()

                _products.value = productData.map {
                    Product(
                        id = it?.id!!,
                        name = it?.name ?: "",
                        price = it?.price?.priceValue ?: 0.0,
                        imageUrl = it?.defaultPictureModel?.imageUrl ?: ""
                    )
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products", e)
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class Product(
    val id: String,
    val name: String,
    val price: Any,
    val imageUrl: String
)
