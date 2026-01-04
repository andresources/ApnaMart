package com.apnamart.feature_auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apnamart.core.common.UiState
import com.apnamart.domain.model.RegisterResult
import com.apnamart.feature_auth.common.RegisterEvent

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit
) {
    val state by viewModel.uiRState.collectAsState()
    LaunchedEffect(state.isLoading) {
        if (!state.isLoading) {
            if(state.isSuccess){
                onRegisterSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onEvent(RegisterEvent.NameChanged(it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.address,
            onValueChange = { viewModel.onEvent(RegisterEvent.AddressChanged(it)) },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.phone,
            onValueChange = { viewModel.onEvent(RegisterEvent.PhoneChanged(it)) },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEvent(RegisterEvent.EmailChanged(it)) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (state.error != null) {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = { viewModel.onEvent(RegisterEvent.Submit) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading)
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            else
                Text("Register")
        }
    }
}

