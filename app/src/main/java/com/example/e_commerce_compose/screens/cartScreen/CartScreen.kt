package com.example.e_commerce_compose.screens.cartScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.e_commerce_compose.R


@Composable
fun CartScreen() {
    Text(text = stringResource(id = R.string.cart_screen_title))
}