package com.apnamart.feature_auth.common

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data object LoginClicked : LoginEvent
}
