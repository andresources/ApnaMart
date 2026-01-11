package com.apnamart.feature_home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.apnamart.feature_home.presentation.UserScoreViewModel

@Composable
fun HomeScreen(name: String, modifier: Modifier = Modifier,viewModel: UserScoreViewModel = hiltViewModel(),goProfile: () -> Unit,goCategory: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val internet by viewModel.isConnected.collectAsState()
    LaunchedEffect(Unit){
        viewModel.loadUsersScore()
    }

    Scaffold(
        containerColor = Color.Blue,
        modifier = Modifier.fillMaxSize(),
        topBar = { Text("Apna App")}
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column{
                LazyColumn {
                    items(state.userScore){ score ->
                        Text(score.email)
                    }
                }
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
            }
        }
    }
}