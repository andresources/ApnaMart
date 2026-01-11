package com.apnamart.feature_auth.data.remote.mapper

import com.apnamart.feature_auth.data.remote.dto.ResponseDto
import com.apnamart.feature_auth.data.remote.dto.UserProfileDto
import com.apnamart.feature_auth.domain.model.Response
import com.apnamart.feature_auth.domain.model.UserProfile

fun ResponseDto.toDomain(): Response {
    return Response(
        status = status,
        message = message
    )
}

fun UserProfileDto.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        name = user_name,
        address = user_address,
        phone = user_phone_number.toString(),
        email = user_email,
        pwd = user_password
    )
}
