package com.apnamart.feature_auth.data.remote.mapper

import com.apnamart.feature_auth.data.remote.dto.RegisterDto
import com.apnamart.feature_auth.domain.model.RegisterResult

fun RegisterDto.toDomain(): RegisterResult {
    return RegisterResult(
        status = status,
        message = message
    )
}
