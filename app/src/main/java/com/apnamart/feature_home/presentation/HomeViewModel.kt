package com.apnamart.feature_home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apnamart.core.common.UiState
import com.apnamart.core.network.di.NetworkMonitor
import com.apnamart.core.presentation.BaseViewModel
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.feature_auth.common.LoginUiState
import com.apnamart.feature_auth.domain.usecase.RegisterUserUseCase
import com.apnamart.feature_home.ScoreUiState
import com.apnamart.feature_home.domain.usecase.HomeUseCase
import com.apnamart.feature_home.domain.usecase.UserScoreUseCase
import com.apnamart.feature_home.presentation.common.HomeSection
import com.apnamart.feature_home.presentation.common.HomeUiState
import com.apnamart.feature_home.presentation.common.ViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _sections = MutableStateFlow<List<HomeSection>>(emptyList())
    val sections = _sections.asStateFlow()

    init {
        loadHome()
    }

    fun loadHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            homeUseCase().collect { result ->
                when (result) {
                    is UiState.Loading -> {
                        Log.i("Dz989","Loding")
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is UiState.Success -> {
                        Log.i("Dz989","Success")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = false,
                                homeData = result.data
                            )
                        }
                        val grouped = result.data.groupBy { it.viewType }

                        val uiSections = listOf(ViewType.Hero, ViewType.Carousal, ViewType.Grid)
                            .mapNotNull { type ->
                                grouped[type.name]?.let {
                                    HomeSection(type, it)
                                }
                            }

                        _sections.value = uiSections
                    }

                    is UiState.Error -> {
                        Log.i("Dz989","Error : ${result.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}