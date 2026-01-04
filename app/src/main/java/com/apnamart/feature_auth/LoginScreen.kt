package com.apnamart.feature_auth


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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apnamart.feature_auth.common.LoginEvent

@Composable
fun LoginScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    gotoRegistration: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading && state.error == null &&
            state.emailOrPhone.isNotBlank()
        ) {
            if(state.isSuccess){
                onLoginSuccess()
            }
        }
    }

    Scaffold(
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Login to continue",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = state.emailOrPhone,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EmailChanged(it))
                },
                label = { Text("Email or Phone") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
                }
            )

            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(LoginEvent.LoginClicked) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login")
                }
            }
            Button(
                onClick = gotoRegistration,
                modifier = Modifier.fillMaxWidth(),
            ) {
                    Text("Register")

            }
        }
    }
}
