package com.apnamart.feature_home.data.mapper

import com.apnamart.feature_home.data.dto.HomeDto
import com.apnamart.feature_home.data.dto.UserScoreDto
import com.apnamart.feature_home.domain.model.HomeModel
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

fun HomeDto.toDomain(): HomeModel {
    return HomeModel(
        id = id.toIntOrNull() ?: 0,
        title = title,
        thumbUrl = thumbUrl,
        originalUrl = originalUrl,
        itemId = itemId.toIntOrNull() ?: 0,
        categoryId = categoryId.toIntOrNull() ?: 0,
        productPrice = productPrice.toIntOrNull() ?: 0,
        offerPrice = offerPrice.toIntOrNull() ?: 0,
        viewType = viewType,
        tower = tower,
        platno = platno,
        dname = dname
    )
}
