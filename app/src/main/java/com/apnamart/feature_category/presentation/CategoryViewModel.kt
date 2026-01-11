package com.apnamart.feature_category.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.core.common.UiState
import com.apnamart.feature_category.domain.usecases.CategoryUseCases
import com.apnamart.feature_category.presentation.common.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            categoryUseCases().collect { result ->
                when (result) {
                    is UiState.Loading -> {
                        Log.i("Dz99","Loding")
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is UiState.Success -> {
                        Log.i("Dz99","Success")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                categories = result.data
                            )
                        }
                        loadCategoryItems(result.data.firstOrNull()?.id?:1)
                    }

                    is UiState.Error -> {
                        Log.i("Dz99","Error : ${result.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadCategoryItems(categoryId: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            categoryUseCases(categoryId).collect { result ->
                when (result) {
                    is UiState.Loading -> {
                        Log.i("Dz99","Loding")
                        _uiState.update { it.copy(isLoading = true,selectedCategoryId = categoryId) }
                    }

                    is UiState.Success -> {
                        Log.i("Dz99","Success")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                items = result.data
                            )
                        }
                    }

                    is UiState.Error -> {
                        Log.i("Dz99","Error : ${result.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }


}
