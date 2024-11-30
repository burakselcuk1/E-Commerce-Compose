package com.example.e_commerce_compose.screens.favoriteScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.e_commerce_compose.R

@Composable
fun FavoritesScreen() {
    Text(text = stringResource(id = R.string.favorites_screen_title))
}