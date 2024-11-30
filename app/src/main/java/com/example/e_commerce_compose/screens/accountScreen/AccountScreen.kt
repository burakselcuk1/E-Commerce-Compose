package com.example.e_commerce_compose.screens.accountScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.e_commerce_compose.R


@Composable
fun AccountScreen() {
    Text(text = stringResource(id = R.string.account_screen_title))
}
