package com.apnamart.feature_auth.common

sealed class RegisterEvent {
    data class NameChanged(val value: String) : RegisterEvent()
    data class AddressChanged(val value: String) : RegisterEvent()
    data class PhoneChanged(val value: String) : RegisterEvent()
    data class EmailChanged(val value: String) : RegisterEvent()
    data class PasswordChanged(val value: String) : RegisterEvent()
    object Submit : RegisterEvent()
}
