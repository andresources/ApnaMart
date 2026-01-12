package com.apnamart.feature_home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.apnamart.feature_auth.presentation.UserAuthViewModel
import com.apnamart.feature_home.presentation.HomeViewModel
import com.apnamart.feature_home.presentation.UserScoreViewModel
import com.apnamart.feature_home.presentation.common.CarouselSectionViewType
import com.apnamart.feature_home.presentation.common.GridSectionViewType
import com.apnamart.feature_home.presentation.common.HeroSectionViewType
import com.apnamart.feature_home.presentation.common.ViewType

@Composable
fun HomeScreen(name: String, modifier: Modifier = Modifier,viewModel: UserScoreViewModel = hiltViewModel(),homeViewModel: HomeViewModel = hiltViewModel(),goProfile: () -> Unit,goCategory: () -> Unit) {
    val state by homeViewModel.uiState.collectAsState()
    val internet by viewModel.isConnected.collectAsState()

    val sections by homeViewModel.sections.collectAsState()
    LaunchedEffect(Unit){
        viewModel.loadUsersScore()
    }

    Scaffold(
        containerColor = Color.Blue,
        modifier = Modifier.safeContentPadding().fillMaxSize(),
        topBar = { Text("Apna App")}
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column{

                Button(onClick = {
                    viewModel.loadUsersScore()
                }) {
                    Text("Submit")
                }

                Button(onClick = goProfile ) {
                    Text("Profile")
                }

                Button(onClick = goCategory) {
                    Text("Category")
                }

                LazyColumn {
                    sections.forEach { section ->
                        when (section.type) {
                            ViewType.Hero -> item {
                                HeroSectionViewType(section.items)
                            }

                            ViewType.Carousal -> item {
                                CarouselSectionViewType(section.items)
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
}