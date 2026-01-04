package com.apnamart.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.data.local.AuthLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authPreferences: AuthLocalDataSource,
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination = _destination.asStateFlow()

    fun decideDestination() {
        viewModelScope.launch {
            delay(2000)
            _destination.value =
                if (authPreferences.isLoggedIn()) SplashDestination.Home
                else SplashDestination.Login
        }
    }
}

sealed class SplashDestination {
    object Login : SplashDestination()
    object Home : SplashDestination()
}
