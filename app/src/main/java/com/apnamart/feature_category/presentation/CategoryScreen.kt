package com.apnamart.feature_category.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apnamart.feature_category.domain.model.CartItem
import com.apnamart.feature_category.domain.model.Category
import com.apnamart.feature_category.domain.model.CategoryItem

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    cartItems: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val cart by viewModel.cart.collectAsState()
    val originalTotal by viewModel.originalTotal.collectAsState()
    val offerTotal by viewModel.offerTotal.collectAsState()

    val differentRestaurent by viewModel.differentRestaurent.collectAsState()
    Column{
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.saveCartItems()
            cartItems()
        }) {
            Text("Cart : ${cart.size}")
        }

        Text("Total : ${offerTotal} [${originalTotal}]")


        if(differentRestaurent && cart.size > 0){
            Text("You are trying from different Restaurent")
        }

        Row(modifier = Modifier.fillMaxSize()) {

            SideCategoryList(
                categories = state.categories,
                selectedId = state.selectedCategoryId,
                onCategoryClick = {
                    viewModel.loadCategoryItems(it)
                }
            )

            Box(modifier = Modifier.weight(1f)) {
                when {
                    state.isLoading -> ShimmerGrid()
                    else -> CategoryGrid(items = state.items,viewModel,cart)
                }
            }
        }
    }
}


@Composable
fun SideCategoryList(
    categories: List<Category>,
    selectedId: Int?,
    onCategoryClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .width(90.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(categories) { category ->

            val selected = category.id == selectedId
            //Start
            val scale by animateFloatAsState(
                targetValue = if (selected) 1.10f else 1f,
                label = ""
            )


            val iconColor by animateColorAsState(
                targetValue = if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                label = ""
            )
            val textColor by animateColorAsState(
                targetValue = if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                label = ""
            )
            //End
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)   // 🔥 THIS is the key
            ){
                if(selected) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .fillMaxHeight()
                            .background(Color.Red)
                    )
                }
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 0.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .background(
                        if (selected)
                            Color.White
                        else
                            Color.Transparent
                    )
                    .clickable {
                        onCategoryClick(category.id)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                AsyncImage(
                    model = category.icon,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(45.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = category.name,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    color = if (selected)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        }
        }
    }

}


@Composable
fun CategoryGrid(items: List<CategoryItem>,viewModel: CategoryViewModel,cart: List<CartItem>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp)
    ) {

        items(items,
            key = { it.id }   // 🔥 required
        ) { item ->
            //val qty = viewModel.getQuantity
            val qty = cart.find { it.id == item.id }?.quantity ?: 0
            CategoryGridItem(item,
                quantity = qty,
                onAdd = {
                    viewModel.addToCart(item)
                },
                onRemove = {
                    viewModel.removeFromCart(item.id)
                }
            )
        }
    }
}

@Composable
fun CategoryGridItem(
    item: CategoryItem,
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
    ) {
    Column(
        modifier = Modifier
            .padding(0.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = item.title + " - "+item.offer_price + " - "+item.original_price,
            fontSize = 12.sp,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (quantity == 0) {
            Button(
                onClick = onAdd,
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
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                    Text("-", color = Color.White)
                }

                Text(
                    text = quantity.toString(),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(onClick = onAdd, modifier = Modifier.size(24.dp)) {
                    Text("+", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun ShimmerGrid() {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(9) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant
                    )
            )
        }
    }
}




