package com.apnamart.data.remote.mapper

import com.apnamart.data.remote.dto.RegisterDto
import com.apnamart.domain.model.RegisterResult

fun RegisterDto.toDomain(): RegisterResult {
    return RegisterResult(
        status = status,
        message = message
    )
}
