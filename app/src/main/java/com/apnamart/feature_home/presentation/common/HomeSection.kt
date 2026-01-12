package com.apnamart.feature_home.presentation.common

import com.apnamart.feature_home.domain.model.HomeModel

data class HomeSection(
    val type: ViewType,
    val items: List<HomeModel>
)