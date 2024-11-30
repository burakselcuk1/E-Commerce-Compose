package com.example.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

open class BaseViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    protected fun <T> launchWithLoading(
        scope: CoroutineScope,
        block: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            setLoading(true)
            try {
                val result = block()
                onSuccess(result)
            } catch (e: Exception) {
                onError(e)
            } finally {
                setLoading(false)
            }
        }
    }

    protected fun <T> launch(
        scope: CoroutineScope,
        block: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                val result = block()
                onSuccess(result)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
