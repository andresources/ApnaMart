package com.apnamart.feature_home.presentation.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import coil.compose.AsyncImage
import com.apnamart.feature_home.domain.model.HomeModel
import com.apnamart.feature_main.HomeSharedViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HeroSectionViewType(data: List<HomeModel>, homeSharedViewModel: HomeSharedViewModel, onItemSelected: (HomeModel) -> Unit) {

    Column{
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        // Auto scroll
        LaunchedEffect(data) {
            while (true) {
                delay(2500)
                val next = (listState.firstVisibleItemIndex + 1) % data.size
                listState.animateScrollToItem(next)
            }
        }

        Column {

            Text(
                "Fresh Vegetables",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            LazyRow(
                state = listState,
                flingBehavior = rememberSnapFlingBehavior(listState),   // snap page
                modifier = Modifier.fillMaxWidth()
            ) {
                items(data) { item ->
                    Column(
                        modifier = Modifier.clickable{
                            homeSharedViewModel.updateSelectedItem(item)
                            onItemSelected(item)
                        }
                            .clip(RoundedCornerShape(8.dp))
                            .width(screenWidth-40.dp)      // 🔥 exactly screen width
                            .height(220.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFFFFF)
                            ),
                            modifier = Modifier.clickable{
                                onItemSelected(item)
                            }.padding(4.dp).padding(4.dp),
                            border = BorderStroke(1.dp, color = Color(0xFFD7D7D7)),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(1.dp)
                        ) {
                            Box(Modifier.fillMaxWidth()) {
                                AsyncImage(
                                    model = item.originalUrl,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),    // fill page
                                    contentScale = ContentScale.Crop
                                )
                                Text("₹${item.productPrice-item.offerPrice} OFF", color = Color.Red,modifier = Modifier.background(shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp), color = Color(0xFFFFFFFF)).padding(horizontal = 8.dp, vertical = 4.dp).align(Alignment.TopCenter))
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Transparent,Color.Black.copy(0.5f))
                                            )
                                        )
                                )
                                Spacer(Modifier.height(6.dp))
                                Column(modifier = Modifier.align(Alignment.BottomStart)){
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
            }


            Spacer(Modifier.height(8.dp))
            CarouselIndicator(data.size, listState.firstVisibleItemIndex)
        }
    }
}
@Composable
fun CarouselItem(item: HomeModel, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = item.originalUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(0.5f))
                    )
                )
        )

        Text(
            item.title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}


@Composable
fun HerorItem(item: HomeModel,modifier: Modifier = Modifier) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(4.dp)) {
            AsyncImage(
                model = item.originalUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(6.dp))

            Text(item.title, fontWeight = FontWeight.Bold)

            Row {
                Text("₹${item.offerPrice}", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(6.dp))
                Text(
                    "₹${item.productPrice}",
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )
            }
        }
    }
}/*
@Composable
fun HeroItem(
    item: HomeModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = item.originalUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            item.title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}*/

@Composable
fun CarouselIndicator(size: Int, index: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(size) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(if (it == index) 10.dp else 8.dp)
                    .background(
                        if (it == index) Color.Green else Color.LightGray,
                        CircleShape
                    )
            )
        }
    }
}
