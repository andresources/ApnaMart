package com.apnamart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apnamart.feature_auth.presentation.ForgotPasswordScreen
import com.apnamart.feature_auth.presentation.LoginScreen
import com.apnamart.feature_auth.presentation.RegisterScreen
import com.apnamart.feature_category.presentation.CategoryScreen
import com.apnamart.feature_home.HomeScreen
import com.apnamart.feature_splash.SplashScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                gotoRegistration = {
                    navController.navigate("register")
                },
                gotoForgotPassword = {
                    navController.navigate("forgot")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }
        composable("forgot") {
            ForgotPasswordScreen(
                onChangedSuccess = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                gotoRegistration = {

                }
            )
        }


        composable("home") {
           // HomeScreen("App")
            CategoryScreen()
        }
    }
}
