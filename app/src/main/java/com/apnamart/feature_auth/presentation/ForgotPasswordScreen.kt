package com.apnamart.feature_auth.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apnamart.R
import com.apnamart.feature_auth.common.ForgotPasswordEvent
import com.apnamart.feature_auth.common.ForgotPasswordUiState
import com.apnamart.feature_auth.common.LoginEvent

@Composable
fun ForgotPasswordScreen(
    viewModel: UserAuthViewModel = hiltViewModel(),
    onChangedSuccess: () -> Unit,
    gotoRegistration: () -> Unit
) {
    val state by viewModel.uiFPState.collectAsState()
    val internet by viewModel.isConnected.collectAsState()

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading && state.error == null &&
            state.email.isNotBlank()
        ) {
            if(state.isSuccess){
                onChangedSuccess()
            }
        }
    }



        Box( modifier = Modifier
            .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .verticalScroll(rememberScrollState())
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(24.dp)
                    .imePadding(), // ✅ APPLY LAST
            ) {
            if(internet.not()){
                Text("Please check Internet connection", color = Color.Red, modifier = Modifier.padding(16.dp))
            }

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "ForgotPassword to continue",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordEvent.EmailChanged(it))
                },
                label = { Text("Email or Phone") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.newpassword,
                label = { Text("New Password") },
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordEvent.NewPasswordChanged(it))
                }
            )

            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.onEvent(ForgotPasswordEvent.SubmitClicked)
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
                    Text("Submit")
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
