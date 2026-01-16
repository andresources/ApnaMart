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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun ImageCarousel(images: List<String> = listOf<String>(
    "https://www.sakshi.com/styles/webp/s3/article_images/2026/01/16/Netherlands.jpg.webp",
    "https://www.sakshi.com/styles/webp/s3/article_images/2026/01/16/ysjagan5.jpg.webp",
    "https://www.sakshi.com/styles/webp/s3/article_images/2026/01/16/egpt.jpg.webp",
    "https://www.sakshi.com/styles/webp/s3/article_images/2026/01/16/Netherlands.jpg.webp",
    "https://www.sakshi.com/styles/webp/s3/article_images/2026/01/16/ysjagan5.jpg.webp",
)) {

    val pagerState = rememberPagerState { 5 }

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
                AsyncImage(
                    model = images[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        /*PagerIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage
        )*/
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


fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}


@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(if (index == currentPage) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) Color.Black else Color.LightGray
                    )
            )
        }
    }
}

