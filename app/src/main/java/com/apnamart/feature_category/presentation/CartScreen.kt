package com.apnamart.feature_category.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun CartScreen(
    viewModel: CategoryViewModel = hiltViewModel()
){
    LaunchedEffect(Unit) {
        viewModel.getCartItems()
    }

}