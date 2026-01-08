package com.apnamart.feature_home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(name: String, modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = Color.Blue,
        modifier = Modifier.fillMaxSize(),
        topBar = { Text("Apna App")}
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = "Hello $name!",
                modifier = modifier
            )
        }
    }
}