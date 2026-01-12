package com.apnamart.feature_home.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.apnamart.feature_home.domain.model.HomeModel

@Composable
fun HeroSectionViewType(data: List<HomeModel>) {
    Column{
        Text("Hero Section")
        LazyRow {
         items(data) { dt->
             Text("Item - ${dt.title}")
         }
        }
    }
}