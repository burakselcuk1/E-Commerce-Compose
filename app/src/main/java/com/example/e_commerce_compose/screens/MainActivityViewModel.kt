package com.example.e_commerce_compose.screens

import com.example.core.BaseViewModel
import com.example.core.LoadingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(loadingManager: LoadingManager
) : BaseViewModel(loadingManager) {

}