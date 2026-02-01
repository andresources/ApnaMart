package com.apnamart.feature_home_details

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.apnamart.feature_cart.presentation.CartViewModel
import com.apnamart.feature_category.presentation.CategoryGrid
import com.apnamart.feature_category.presentation.CategoryViewModel
import com.apnamart.feature_home.domain.model.HomeModel
import com.apnamart.feature_home.domain.model.homeToCategoryItem
import com.apnamart.feature_main.CartIconStack
import com.apnamart.feature_main.HomeSharedViewModel
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeDetailsScreen(homeSharedViewModel: HomeSharedViewModel,categoryViewModel: CategoryViewModel = hiltViewModel(),cartViewModel: CartViewModel) {
    val item by homeSharedViewModel.selectedItem.collectAsState()
    val uiHomeCategoryState by categoryViewModel.uiState.collectAsState()
    val cart by cartViewModel.cart.collectAsState()
    val qty = cart.find { it.id == item?.id }?.quantity ?: 0
    val originalTotal by cartViewModel.originalTotal.collectAsState()
    val offerTotal by cartViewModel.offerTotal.collectAsState()
    var showCart by remember{
        mutableStateOf(false)
    }

    val differentRestaurent by cartViewModel.differentRestaurent.collectAsState()
    LaunchedEffect(Unit) {
        item?.let {
            categoryViewModel.loadCategoryItems(it.categoryId)
        }
    }
    Box(modifier = Modifier.background(Color.White).padding(
        bottom = 16.dp
    ).fillMaxSize()){
    Column {
        item?.let {
            DetailImage(it,cartViewModel,qty)
        }
        /*Button(onClick = {
            cartViewModel.saveCartItems()
        }) {
            Text("Cart : ${cart.size}")
        }*/
        /*if (differentRestaurent && cart.size > 0) {
            Text("You are trying from different Restaurent")
        }*/
        CategoryGrid(items = uiHomeCategoryState.items, cart, cartViewModel)

    }
        if (cart.size > 0) {
            showCart = true
        } else {
            showCart = false
        }
        if (showCart) {

            Row(
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
                    .fillMaxWidth(0.65f).padding(8.dp)
            ) {
                CartIconStack(cart, cartViewModel = cartViewModel)
            }

        }
}

}

@Composable
fun DetailImage(item: HomeModel,cartViewModel: CartViewModel,quantity: Int){
    Log.i("Dz00","ID : ${item.id} - categoryId: ${item.categoryId}")
    Column(
        modifier = Modifier.clickable{
            //homeSharedViewModel.updateSelectedItem(item)
            //onItemSelected(item)
        }.padding(top = 32.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(220.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            modifier = Modifier.clickable{
                //onItemSelected(item)
            }.padding(4.dp),
            border = BorderStroke(1.dp, color = Color(0xFFD7D7D7)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Box(Modifier.padding(2.dp).fillMaxWidth()) {
                AsyncImage(
                    model = item.originalUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),    // fill page
                    contentScale = ContentScale.Crop
                )

                //Text("${c}")
                Text(
                    "₹${item.productPrice - item.offerPrice} OFF",
                    color = Color.Red,
                    modifier = Modifier.background(
                        shape = RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        ), color = Color(0xFFFFFFFF)
                    ).padding(horizontal = 8.dp, vertical = 4.dp).align(Alignment.TopCenter)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Transparent, Color.Black.copy(0.5f))
                            )
                        )
                )
                if (quantity == 0) {
                    Button(
                        onClick = {
                            cartViewModel.addToCart(item.homeToCategoryItem())
                        },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.padding(4.dp).align(Alignment.BottomEnd).height(28.dp)
                    ) {
                        Text("ADD", fontSize = 12.sp)
                    }
                } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp).align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    IconButton(onClick = {
                        cartViewModel.removeFromCart(item.id)
                    }, modifier = Modifier.size(24.dp)) {
                        Text("-", color = Color.White)
                    }

                    Text(
                        text = quantity.toString(),//quantity.toString()
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(
                        onClick = {//onAdd
                            cartViewModel.addToCart(item.homeToCategoryItem())
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Text("+", color = Color.White)
                    }
                }
            }
                Spacer(Modifier.height(6.dp))
                Column(modifier = Modifier.padding(8.dp).align(Alignment.BottomStart)){
                    Text(item.title, fontSize = 24.sp, fontWeight = FontWeight.Bold,color = Color.White)
                    Row{
                        Text("₹${item.offerPrice}", fontWeight = FontWeight.Bold, color =
                            Color.White)
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "₹${item.productPrice}",
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.White
                        )
                    }
                }

            }
        }
    }
}