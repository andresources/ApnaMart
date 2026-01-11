package com.apnamart.feature_auth.common


sealed interface ForgotPasswordEvent {
    data class EmailChanged(val value: String) : ForgotPasswordEvent
    data class NewPasswordChanged(val value: String) : ForgotPasswordEvent
    data object SubmitClicked : ForgotPasswordEvent
}