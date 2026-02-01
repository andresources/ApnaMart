package com.apnamart.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TowerDetails(){
    Column(
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x99000000), // Top
                    Color(0x55000000)  // Bottom
                )
            )
        ).padding(8.dp)
    ){

            Text(text = "T2", fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 14.sp, color = Color.White)
            Text(text = "211", fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 14.sp, color = Color.White)

    }
}