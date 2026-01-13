package com.apnamart.feature_home.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apnamart.core.presentation.components.shimmerBackground
import com.apnamart.feature_home.domain.model.HomeModel

@Composable
fun CarouselSectionViewType(data: List<HomeModel>,onAddClick: (HomeModel) -> Unit) {
    Column{
        Text("Carousel Section")
        LazyRow {
            items(data) { dt->
                CarouselItem(dt,onAddClick = onAddClick)
            }
        }
    }
}

@Composable
fun CarouselItem(
    item: HomeModel,
    onAddClick: (HomeModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp).clickable{
            onAddClick(item)
        },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box {
                AsyncImage(
                    model = item.thumbUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(130.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                if (item.discount > 0) {
                    Box(
                        modifier = Modifier
                            .background(Color.Red, RoundedCornerShape(bottomEnd = 8.dp))
                            .padding(horizontal = 4.dp)
                            .shimmerBackground()
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            text = "₹${item.discount} OFF",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Row{
                Text(
                    text = "₹${item.offerPrice}",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier= Modifier.width(16.dp))
                Text(
                    textDecoration = TextDecoration.LineThrough,
                    text = "₹${item.productPrice}",
                    color = Color(0xFFA1A1A1),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}