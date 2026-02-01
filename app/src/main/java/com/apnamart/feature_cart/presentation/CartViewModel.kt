package com.apnamart.feature_cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.data.local.AuthLocalDataSource
import com.apnamart.feature_category.domain.model.CartItem
import com.apnamart.feature_category.domain.model.CategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.times

@HiltViewModel
class CartViewModel @Inject constructor(
    private val authPreferences: AuthLocalDataSource,
) : ViewModel(){
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart = _cart.asStateFlow()

    private val _differentRestaurent = MutableStateFlow<Boolean>(false)
    val differentRestaurent = _differentRestaurent.asStateFlow()

    private val _offerTotal = MutableStateFlow<Int>(0)
    val offerTotal = _offerTotal.asStateFlow()

    private val _originalTotal = MutableStateFlow<Int>(0)
    val originalTotal = _originalTotal.asStateFlow()

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

    fun clearCart(){
        _cart.value = emptyList()
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
                    offer_price = item.offer_price,
                    tower = item.tower,
                    platno = item.platno,
                    dname = item.dname
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
}
