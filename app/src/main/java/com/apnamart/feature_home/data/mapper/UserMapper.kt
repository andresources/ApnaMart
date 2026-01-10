package com.apnamart.feature_home.data.mapper

import com.apnamart.feature_home.data.dto.UserScoreDto
import com.apnamart.feature_home.domain.model.UserScore

fun UserScoreDto.toDomain(): UserScore {
    return UserScore(
        id = id.toInt(),
        email = user_email,
        model = model_test,
        score = score.toInt(),
        total = total.toInt()
    )
}
