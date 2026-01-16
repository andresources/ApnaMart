package com.apnamart.feature_home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apnamart.feature_home.domain.model.HomeModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun ImageCarousel(data: List<HomeModel>) {

    val pagerState = rememberPagerState { data.size }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Column{
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 2.dp),
            modifier = Modifier.fillMaxWidth(),
            pageSize = PageSize.Fill
        ) { page ->
            val pageOffset =
                ((pagerState.currentPage - page) +
                        pagerState.currentPageOffsetFraction).absoluteValue
            Card(
                modifier = Modifier
                    .fillMaxWidth().padding(2.dp)
                    .height(180.dp).graphicsLayer {
                        rotationY = pageOffset * 40f
                        scaleX = 1f - (pageOffset * 0.15f)
                        scaleY = 1f - (pageOffset * 0.15f)
                        alpha = 1f - pageOffset * 0.4f
                        cameraDistance = 16 * density
                    }
            ) {
                Box(Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = data[page].originalUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),    // fill page
                        contentScale = ContentScale.Crop
                    )
                    Text("₹${data[page].productPrice-data[page].offerPrice} OFF", color = Color.Red,modifier = Modifier.background(shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp), color = Color(0xFFFFFFFF)).padding(horizontal = 8.dp, vertical = 4.dp).align(Alignment.TopCenter))
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
                    Row(modifier = Modifier.padding(4.dp).align(Alignment.BottomStart),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(data[page].title, fontSize = 24.sp, fontWeight = FontWeight.Bold,color = Color.White)
                        Spacer(modifier = Modifier.width(16.dp))
                        Row{
                            Text("₹${data[page].offerPrice}", fontWeight = FontWeight.Bold, color =
                                Color.White)
                            Spacer(Modifier.width(4.dp))
                            Text(
                                "₹${data[page].productPrice}",
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.White
                            )
                        }
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row{
            Spacer(modifier = Modifier.weight(1f))
            LinePagerIndicator(pagerState  = pagerState)
            Spacer(modifier = Modifier.weight(1f))
        }

    }

}
@Composable
fun LinePagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pagerState.pageCount) { index ->
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(if (pagerState.currentPage == index) 24.dp else 8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (pagerState.currentPage == index)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.LightGray
                    )
            )
        }
    }
}

