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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apnamart.R
import com.apnamart.feature_auth.common.LoginEvent

@Composable
fun LoginScreen(
    viewModel: UserAuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    gotoRegistration: () -> Unit,
    gotoForgotPassword: ()-> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val internet by viewModel.isConnected.collectAsState()

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading && state.error == null && state.emailOrPhone.isNotBlank()
        ) {
            if(state.isSuccess){
                onLoginSuccess()
            }
        }
    }

      Box( modifier = Modifier
          .fillMaxSize()
      ){
          Image(
              painter = painterResource(id = R.drawable.ic_bg),
              contentDescription = null,
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Crop
          )

          // 🔥 Login Card
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
                  text = "Login to continue",
                  style = MaterialTheme.typography.bodyMedium
              )

              Spacer(Modifier.height(16.dp))

              OutlinedTextField(
                  value = state.emailOrPhone,
                  onValueChange = {
                      viewModel.onEvent(LoginEvent.EmailChanged(it))
                  },
                  label = { Text("Email or Phone") },
                  singleLine = true,
                  modifier = Modifier.fillMaxWidth()
              )

              Spacer(Modifier.height(8.dp))

              var passwordVisible by remember { mutableStateOf(false) }

              OutlinedTextField(
                  modifier = Modifier.fillMaxWidth(),
                  label = { Text("Password") },
                  singleLine = true,
                  value = state.password,
                  onValueChange = {
                      viewModel.onEvent(LoginEvent.PasswordChanged(it))
                  },
                  visualTransformation =
                      if (passwordVisible) VisualTransformation.None
                      else PasswordVisualTransformation(),
                  trailingIcon = {
                      IconButton(onClick = { passwordVisible = !passwordVisible }) {
                          Icon(
                              imageVector = if (passwordVisible)
                                  Icons.Default.Visibility
                              else
                                  Icons.Default.VisibilityOff,
                              contentDescription = if (passwordVisible)
                                  "Hide password"
                              else
                                  "Show password"
                          )
                      }
                  }
              )


              state.error?.let {
                  Spacer(Modifier.height(8.dp))
                  Text(text = it, color = MaterialTheme.colorScheme.error)
              }

              Spacer(Modifier.height(24.dp))

              Button(
                  onClick = {
                      viewModel.onEvent(LoginEvent.LoginClicked)
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
                      Text("Login")
                  }
              }
              Button(
                  onClick = gotoRegistration,
                  modifier = Modifier.fillMaxWidth(),
              ) {
                  Text("Register")

              }
              Button(
                  onClick = gotoForgotPassword,
                  modifier = Modifier.fillMaxWidth(),
              ) {
                  Text("Forgot Password")

              }
          }
      }

}
