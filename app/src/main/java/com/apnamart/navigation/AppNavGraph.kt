package com.apnamart.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apnamart.feature_auth.presentation.ForgotPasswordScreen
import com.apnamart.feature_auth.presentation.LoginScreen
import com.apnamart.feature_auth.presentation.RegisterScreen
import com.apnamart.feature_auth.presentation.UserProfileScreen
import com.apnamart.feature_cart.presentation.CartViewModel
import com.apnamart.feature_category.presentation.CartScreen
import com.apnamart.feature_category.presentation.CategoryScreen
import com.apnamart.feature_home.HomeScreen
import com.apnamart.feature_home_details.HomeDetailsScreen
import com.apnamart.feature_main.HomeSharedViewModel
import com.apnamart.feature_main.MainScreen
import com.apnamart.feature_splash.SplashScreen
import com.google.gson.Gson

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    var homeSharedViewModel: HomeSharedViewModel = hiltViewModel()
    var cartViewModel: CartViewModel = hiltViewModel()

    SharedTransitionLayout {
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
                        navController.navigate("main") {
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
                        navController.navigate("main") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }
            composable("forgot") {
                ForgotPasswordScreen(
                    onChangedSuccess = {
                        navController.navigate("main") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    gotoRegistration = {

                    }
                )
            }

            composable("main") {
                MainScreen(homeSharedViewModel,cartViewModel) {
                    navController.navigate("homedetailspage")
                }
            }

            composable("homedetailspage") {
                HomeDetailsScreen(homeSharedViewModel,cartViewModel = cartViewModel)
            }
        }
    }
}
