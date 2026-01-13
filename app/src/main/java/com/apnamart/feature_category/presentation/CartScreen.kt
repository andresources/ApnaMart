package com.apnamart.feature_category.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun CartScreen(
    viewModel: CategoryViewModel = hiltViewModel()
){
    /*LaunchedEffect(Unit) {
        viewModel.getCartItems()
    }*/
    Column(modifier = Modifier.fillMaxSize().background(Color.Red)){
        Text("Cart Screen")
    }

}