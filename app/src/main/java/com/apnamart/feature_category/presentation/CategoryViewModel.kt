package com.apnamart.feature_category.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.core.common.UiState
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.feature_category.domain.model.CartItem
import com.apnamart.feature_category.domain.model.CategoryItem
import com.apnamart.feature_category.domain.usecases.CategoryUseCases
import com.apnamart.feature_category.presentation.common.CategoryUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val authPreferences: AuthLocalDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart = _cart.asStateFlow()


    private val _differentRestaurent = MutableStateFlow<Boolean>(false)
    val differentRestaurent = _differentRestaurent.asStateFlow()

    private val _offerTotal = MutableStateFlow<Int>(0)
    val offerTotal = _offerTotal.asStateFlow()

    private val _originalTotal = MutableStateFlow<Int>(0)
    val originalTotal = _originalTotal.asStateFlow()

    init {
        loadCategories()
    }

    fun calculateTotal(){
        val offerTotal = cart.value.sumOf {
                item ->
            item.offer_price * item.quantity
        }
        _offerTotal.value = offerTotal

        val originalTotal = cart.value.sumOf {
                item ->
            item.original_price * item.quantity
        }
        _originalTotal.value = originalTotal
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

    fun storeCartItems(){
        val gson = Gson()
        val json = gson.toJson(cart.value)
    }
    fun addToCart(item: CategoryItem) {
        val newRestaurentAdded = cart.value.any {
            it.categoryId != item.categoryId
        }
        if(newRestaurentAdded){
            _differentRestaurent.value = true
            return
        }else{
            _differentRestaurent.value = false
        }

        _cart.update { cart ->
            val existing = cart.find { it.id == item.id }
            if (existing != null) {
                cart.map {
                    if (it.id == item.id)
                        it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                cart + CartItem(
                    id = item.id,
                    title = item.title,
                    imageUrl = item.imageUrl,
                    quantity = 1,
                    categoryId = item.categoryId,
                    original_price = item.original_price,
                    offer_price = item.offer_price
                )
            }
        }
        calculateTotal()
    }

    fun removeFromCart(itemId: Int) {
        _cart.update { cart ->
            cart.mapNotNull {
                if (it.id == itemId) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1)
                    else null
                } else it
            }
        }
        calculateTotal()
    }

    fun saveCartItems(){
        viewModelScope.launch {
            authPreferences.saveCartItems(cart.value)
        }
    }

    fun getCartItems(){
        viewModelScope.launch {
            var cartItems = authPreferences.getCartItems()
            Log.i("Dz99","cartItems : $cartItems")
        }
    }

}
