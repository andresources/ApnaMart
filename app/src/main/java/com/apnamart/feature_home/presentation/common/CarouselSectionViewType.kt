package com.apnamart.feature_home.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.apnamart.feature_home.domain.model.HomeModel

@Composable
fun CarouselSectionViewType(data: List<HomeModel>) {
    Column{
        Text("Carousel Section")
        /*LazyColumn {
            items(data) { dt->
                Text("Item - ${dt.title}")
            }
        }*/
    }
}