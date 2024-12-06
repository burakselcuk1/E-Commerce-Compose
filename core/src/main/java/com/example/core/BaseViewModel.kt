package com.example.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor(
    private val loadingManager: LoadingManager
) : ViewModel() {

    val isLoading: StateFlow<Boolean> = loadingManager.isLoading

    protected fun setLoading(loading: Boolean) {
        loadingManager.setLoading(loading)
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