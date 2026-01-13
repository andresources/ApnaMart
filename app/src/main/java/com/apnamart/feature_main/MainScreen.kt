package com.apnamart.feature_main

import android.app.Activity
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apnamart.feature_auth.presentation.UserProfileScreen
import com.apnamart.feature_category.presentation.CartScreen
import com.apnamart.feature_category.presentation.CategoryScreen
import com.apnamart.feature_home.HomeScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val statusBarColors = mapOf(
        BottomNavItem.Home.route to Color(0xFF1976D2),     // Blue
        BottomNavItem.Category.route to Color(0xFFFF9800),// Orange
        BottomNavItem.Cart.route to Color(0xFF2E7D32),    // Green
        BottomNavItem.Profile.route to Color(0xFF7B1FA2)
    )

    val colors = listOf(
        Color(0xFF1976D2),
        Color(0xFFFF9800),
        Color(0xFF2E7D32),
        Color(0xFF7B1FA2)
    )
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val navController = rememberNavController()
    val view = LocalView.current
    var previousTabIndex by remember { mutableIntStateOf(0) }
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val targetColor = statusBarColors[currentRoute] ?: Color.Transparent

    val animatedStatusBarColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(60),
        label = "statusBar"
    )
    var currentIndex = items.indexOfFirst { it.route == currentRoute }
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colors[currentIndex].toArgb()
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = animatedStatusBarColor.luminance() > 0.5f
        }
    }

    currentIndex = if(currentIndex == -1) 0 else currentIndex
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SwiggyTopBar(scrollBehavior,currentIndex,colors)
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        val currentIndex = items.indexOfFirst { it.route == currentRoute }
        val isForward = currentIndex > previousTabIndex
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            items.forEach { item ->

                composable(
                    route = item.route,
                    enterTransition = {
                        if (isForward)
                            slideInHorizontally { it } + fadeIn()
                        else
                            slideInHorizontally { -it } + fadeIn()
                    },
                    exitTransition = {
                        if (isForward)
                            slideOutHorizontally { -it } + fadeOut()
                        else
                            slideOutHorizontally { it } + fadeOut()
                    }
                ) {
                    when (item) {
                        BottomNavItem.Home -> HomeScreen("App")
                        BottomNavItem.Category -> CategoryScreen {

                        }
                        BottomNavItem.Cart -> CartScreen()
                        BottomNavItem.Profile -> UserProfileScreen()
                    }
                }
            }

        }
    }
}


sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Cart : BottomNavItem("cart", "Cart", Icons.Filled.ShoppingCart)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person)

    object Category : BottomNavItem("category", "Category", Icons.Filled.Apps)
}

val items = listOf(
    BottomNavItem.Home,
    BottomNavItem.Category,
    BottomNavItem.Cart,
    BottomNavItem.Profile
)

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    NavigationBar {

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    val newIndex = items.indexOf(item)
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(item.icon, contentDescription = item.title)
                },
                label = { Text(item.title) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwiggyTopBar(scrollBehavior: TopAppBarScrollBehavior,cnt: Int, colors: List<Color>) {

    LargeTopAppBar(
        title = {
            Column {
                Text("Deliver To", fontSize = 12.sp, color = Color.White)
                Text("ReddyHarry, Hyderabad", fontWeight = FontWeight.Bold,color = Color.White)
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ShoppingCart, null, tint = Color.White)
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = colors.get(cnt),      // 🔥 makes it immersive
            scrolledContainerColor = colors.get(cnt)
        )
    )
}



