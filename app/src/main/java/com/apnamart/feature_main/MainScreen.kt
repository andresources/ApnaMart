package com.apnamart.feature_main

import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.apnamart.feature_cart.presentation.CartViewModel
import com.apnamart.feature_category.domain.model.CartItem
import com.apnamart.feature_category.domain.model.CategoryItem
import com.apnamart.feature_category.presentation.CategoryViewModel
import com.apnamart.feature_home_details.HomeDetailsScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(homeSharedViewModel: HomeSharedViewModel, cartViewModel: CartViewModel,goToDetailsScreen: () -> Unit) {
    val cart by cartViewModel.cart.collectAsState()
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
        Color(0xFF7B1FA2),
    )
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val navController = rememberNavController()
    var showCart by remember { mutableStateOf(false) }
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
        Box(modifier = Modifier.padding(padding)) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
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
                        BottomNavItem.Home -> HomeScreen(homeSharedViewModel = homeSharedViewModel) { selectedItem ->
                            homeSharedViewModel.updateSelectedItem(selectedItem)
                            Log.i("Dz55", "selectedItem : ${selectedItem}")
                            goToDetailsScreen.invoke()
                        }

                        BottomNavItem.Category -> CategoryScreen(cartViewModel = cartViewModel) {

                        }

                        BottomNavItem.Cart -> CartScreen()
                        BottomNavItem.Profile -> UserProfileScreen()
                    }
                }
            }

        }
            if(cart.size > 0 && currentIndex < 2) {
                showCart = true
            }else{
                showCart = false
            }
            if(showCart){

                    Row(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
                            .fillMaxWidth(0.65f).padding(8.dp)
                    ) {
                        CartIconStack(cart,cartViewModel = cartViewModel)
                    }

            }

        }
    }
}

@Composable
fun CartIconStack(
    cartItems: List<CartItem>, // image urls or resource ids
    cartViewModel: CartViewModel,
) {
    var showBSheet by remember {
        mutableStateOf(false)
    }
    if(showBSheet){

               CartBottomSheet(showSheet = showBSheet,cartItems,cartViewModel,onDismiss = {
                   showBSheet = false
               })

    }
    val totalSavings = cartItems.sumOf { item ->
        (item.original_price - item.offer_price) * item.quantity
    }
    val totalOriginal = cartItems.sumOf { it.original_price * it.quantity }
    val totalOffer = cartItems.sumOf { it.offer_price * it.quantity }
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)).background(Color.Red)
        ) {
           Text("Saved $totalSavings",color = Color.White, modifier = Modifier.clip(
               RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
           ).background(Color.Red).padding(horizontal = 16.dp))
        }
        Row(modifier = Modifier.clip(RoundedCornerShape(25.dp)).background(Color.Red).padding(start = 8.dp).fillMaxWidth(),
verticalAlignment = Alignment.CenterVertically
        ){
            CartIconStack1(cartItems)
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text("Items: ${cartItems.size}", color = Color.White,fontSize = 12.sp)
                //Text("$totalOffer",color = Color.White,fontSize = 8.sp)
                /*Row {
                    Text("$totalOffer",color = Color.White,fontSize = 8.sp)
                    Text("$totalOriginal",fontSize = 8.sp,textDecoration = TextDecoration.LineThrough,color = Color.White)
                }*/
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                showBSheet = true
            }) {
                Icon(Icons.Default.ArrowForward, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }

    }
}

@Composable
fun CartIconStack1(cartItems: List<CartItem>,maxVisible: Int = 3,){
    Row(
        horizontalArrangement = Arrangement.spacedBy((-15).dp),
        modifier = Modifier.animateContentSize()
    ) {
        val remaining = cartItems.size - maxVisible
        cartItems.take(maxVisible).forEach { image ->
            AsyncImage(
                model = image.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),    // fill page
                contentScale = ContentScale.Crop
            )
        }
        if (remaining > 0) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+$remaining",
                    color = Color.White,
                    fontSize = 12.sp
                )
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
    BottomNavItem.Profile,
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

//Cart Bottom Sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheet(
    showSheet: Boolean,
    cartItems: List<CartItem>,
    cartViewModel: CartViewModel,
    onDismiss: () -> Unit,
    onCheckoutClick: () -> Unit = {}
) {
    if (!showSheet) return

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    val scope = rememberCoroutineScope()
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()   // 👈 animate down
                    onDismiss.invoke()
                }
            },
            sheetState = sheetState,
            dragHandle = null,
            containerColor = Color.Transparent,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {

                CartSheetContent(
                    onDismiss,
                    cartItems = cartItems,
                    cartViewModel,
                    onCheckoutClick = onCheckoutClick
                )

        }
}

@Composable
fun CartSheetContent(
    onDismiss: () -> Unit,
    cartItems: List<CartItem>,
    cartViewModel: CartViewModel,
    onCheckoutClick: () -> Unit
) {
    val totalAmount = cartItems.sumOf { it.offer_price * it.quantity }
    val totalSavings = cartItems.sumOf {
        (it.original_price - it.offer_price) * it.quantity
    }
    val density = LocalDensity.current
    val screenHeightPx = with(density) {
        LocalConfiguration.current.screenHeightDp.dp - 100.dp
    }
    Column(
        modifier = Modifier.background(Color.Transparent)
            .fillMaxWidth()
            .heightIn(max = screenHeightPx),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .size(30.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(Icons.Default.Close, null)
        }
        Spacer(Modifier.height(16.dp))
    Column(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)).background(Color.Red)
            .fillMaxWidth()

    ) {
        Text(
            text = "Your Cart",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Divider()

        // Cart Items
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cartItems, key = { it.id }) { item ->
                CartItemView(item, onAdd = { item ->
                    val categoryItem = CategoryItem(
                        id = item.id,
                        title = item.title,
                        imageUrl = item.imageUrl,
                        categoryId = item.categoryId,
                        original_price = item.original_price,
                        offer_price = item.offer_price
                    )
                    cartViewModel.addToCart(categoryItem)
                }) {
                        item ->
                    cartViewModel.removeFromCart(item.id)
                }
            }
        }

        Divider()

        // Sticky Summary
        CartSummary(
            totalAmount = totalAmount,
            totalSavings = totalSavings,
            onCheckoutClick = onCheckoutClick
        )
    }
}
}
@Composable
fun CartItemView(item: CartItem,onAdd: (CartItem) -> Unit,onRemove: (CartItem) -> Unit,) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.title,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontWeight = FontWeight.SemiBold)

            Row {
                Text(
                    "₹${item.offer_price}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "₹${item.original_price}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Text(
                "Qty: ${item.quantity}",
                fontSize = 12.sp,
                color = Color.Gray
            )

        }
        Column{
            Text(
                "₹${item.offer_price * item.quantity}",
                fontWeight = FontWeight.Bold
            )
            if (item.quantity == 0) {
                Button(
                    onClick = {onAdd.invoke(item)},
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text("ADD", fontSize = 12.sp)
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    IconButton(onClick = {onRemove.invoke(item)}, modifier = Modifier.size(24.dp)) {
                        Text("-", color = Color.White)
                    }

                    Text(
                        text = item.quantity.toString(),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(onClick = {onAdd.invoke(item)}, modifier = Modifier.size(24.dp)) {
                        Text("+", color = Color.White)
                    }
                }
            }
        }

    }
}

@Composable
fun CartSummary(
    totalAmount: Int,
    totalSavings: Int,
    onCheckoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Saved")
            Text(
                "₹$totalSavings",
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", fontWeight = FontWeight.Bold)
            Text(
                "₹$totalAmount",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCheckoutClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Proceed to Checkout")
        }
    }
}





