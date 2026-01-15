package com.apnamart.feature_main

import androidx.lifecycle.ViewModel
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.feature_home.domain.model.HomeModel
import com.apnamart.feature_splash.SplashDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

    @HiltViewModel
    class HomeSharedViewModel @Inject constructor() : ViewModel() {
        private val _selectedItem = MutableStateFlow<HomeModel?>(null)
        val selectedItem = _selectedItem.asStateFlow()

        fun updateSelectedItem(homeModel: HomeModel){
            _selectedItem.value= homeModel
        }
    }