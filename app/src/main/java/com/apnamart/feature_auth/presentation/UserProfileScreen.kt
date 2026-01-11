package com.apnamart.feature_auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apnamart.feature_auth.common.LoginEvent
import com.apnamart.feature_auth.common.ProfileEvent
import com.apnamart.feature_auth.common.ProfileUiState

@Composable
fun UserProfileScreen(
    viewModel: UserAuthViewModel = hiltViewModel()
){
    val state by viewModel.uiProfileState.collectAsState()
    val internet by viewModel.isConnected.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (internet.not()) {
            Text(
                "Please check Internet connection",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = state.user_name,
            onValueChange = {
                viewModel.onEvent(ProfileEvent.UsernameChanged(it))
            },
            label = { Text("Email or Phone") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = state.user_address,
            onValueChange = {
                viewModel.onEvent(ProfileEvent.UserAddressChanged(it))
            },
            label = { Text("Address") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = state.user_phone_number,
            onValueChange = {
                viewModel.onEvent(ProfileEvent.UserPhoneNumberChanged(it))
            },
            label = { Text("Phone Number") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = state.user_email,
            enabled = false,
            onValueChange = {
                viewModel.onEvent(ProfileEvent.UserEmailChanged(it))
            },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = state.user_password,
            onValueChange = {
                viewModel.onEvent(ProfileEvent.UserPasswordChanged(it))
            },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.onEvent(ProfileEvent.UpdateProfileClicked)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Update Profile")
            }
        }
    }
}