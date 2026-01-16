package com.apnamart.feature_home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apnamart.feature_auth.presentation.UserAuthViewModel
import com.apnamart.feature_home.domain.model.HomeModel
import com.apnamart.feature_home.presentation.HomeViewModel
import com.apnamart.feature_home.presentation.UserScoreViewModel
import com.apnamart.feature_home.presentation.common.CarouselSectionViewType
import com.apnamart.feature_home.presentation.common.GridSectionViewType
import com.apnamart.feature_home.presentation.common.HeroSectionViewType
import com.apnamart.feature_home.presentation.common.ViewType
import com.apnamart.feature_main.HomeSharedViewModel
import com.apnamart.feature_splash.SplashViewModel

@Composable
fun HomeScreen(viewModel: UserScoreViewModel = hiltViewModel(),homeViewModel: HomeViewModel = hiltViewModel(), homeSharedViewModel: HomeSharedViewModel,onItemSelected: (HomeModel) -> Unit) {
    val state by homeViewModel.uiState.collectAsState()
    val internet by viewModel.isConnected.collectAsState()

    val sections by homeViewModel.sections.collectAsState()
    LaunchedEffect(Unit){
        viewModel.loadUsersScore()
    }

        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            Column{

                LazyColumn(contentPadding = PaddingValues(
                    bottom = 90.dp
                )) {
                    item{

                    }
                    sections.forEach { section ->
                        when (section.type) {
                            ViewType.Hero -> item {
                                /*HeroSectionViewType(section.items,homeSharedViewModel){ selectedItem ->
                                    onItemSelected(selectedItem)
                                }*/
                                ImageCarousel(section.items)
                            }

                            ViewType.Carousal -> item {
                                CarouselSectionViewType(section.items){
                                    Log.i("Dz99","$it")
                                }
                            }

                            ViewType.Grid -> item {
                                GridSectionViewType(section.items){
                                    Log.i("Dz99","$it")
                                }
                            }
                        }
                    }
                }
            }
        }
}