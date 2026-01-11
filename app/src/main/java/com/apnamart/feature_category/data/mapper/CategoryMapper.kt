package com.apnamart.feature_category.data.mapper

import com.apnamart.feature_category.data.dto.CategoryDto
import com.apnamart.feature_category.data.dto.CategoryItemDto
import com.apnamart.feature_category.domain.model.Category
import com.apnamart.feature_category.domain.model.CategoryItem

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        icon = icon
    )
}

fun CategoryItemDto.toDomain(): CategoryItem {
    return CategoryItem(
        id = id,
        title = title,
        imageUrl = image_url,
        categoryId = category_id
    )
}
