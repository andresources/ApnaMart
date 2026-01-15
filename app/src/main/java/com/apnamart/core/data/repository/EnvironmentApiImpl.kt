package com.apnamart.core.data.repository

import com.apnamart.core.domain.repository.EnvironmentApi

class EnvironmentApiImpl : EnvironmentApi {
    override fun getBuildType(): String = "ProdBT"
}