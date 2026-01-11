package com.apnamart.feature_auth.common

sealed interface ProfileEvent {
    data class UsernameChanged(val value: String) : ProfileEvent
    data class UserAddressChanged(val value: String) : ProfileEvent
    data class UserPhoneNumberChanged(val value: String) : ProfileEvent
    data class UserEmailChanged(val value: String) : ProfileEvent
    data class UserPasswordChanged(val value: String) : ProfileEvent
    data object UpdateProfileClicked : ProfileEvent
}