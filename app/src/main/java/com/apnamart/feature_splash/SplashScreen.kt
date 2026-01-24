package com.apnamart.feature_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.apnamart.R
import com.apnamart.ui.theme.Green

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.decideDestination()
    }

    val destination by viewModel.destination.collectAsState()

    destination?.let {
        LaunchedEffect(it) {
            when (it) {
                SplashDestination.Home -> {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                }

                SplashDestination.Login -> {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.background(Green).fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_splash),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
    }
}
