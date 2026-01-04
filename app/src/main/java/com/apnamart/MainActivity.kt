package com.apnamart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apnamart.feature_auth.LoginScreen
import com.apnamart.feature_auth.RegisterScreen
import com.apnamart.feature_splash.SplashScreen
import com.apnamart.ui.theme.ApnaMartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen() // Android 12 system splash
        setContent {
            ApnaMartTheme {
                    Box(

                    ){
                        //LoginScreen()
                        var navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "splash"
                        ) {
                            composable("splash") { SplashScreen(navController) }
                            composable("login") { LoginScreen(onLoginSuccess = {
                                navController.navigate("home")
                            }){
                                navController.navigate("register")
                            }
                            }
                            composable("register") { RegisterScreen {
                                navController.navigate("home")
                            } }
                            composable("home") { HomeScreen("App") }
                        }

                    }

            }
        }
    }
}

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
