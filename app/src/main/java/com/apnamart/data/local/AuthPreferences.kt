package com.apnamart.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.apnamart.feature_category.domain.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore by preferencesDataStore("auth_prefs")

    private val USER_NAME = stringPreferencesKey("user_name")

    private val CART_ITEMS = stringPreferencesKey("cart_items")

    suspend fun saveUser(user: String) {
        context.dataStore.edit {
            it[USER_NAME] = user
        }
    }

    suspend fun saveCartItems(items: List<CartItem>) {
        val gson = Gson()
        val json = gson.toJson(items)
        context.dataStore.edit {
            it[CART_ITEMS] = json
        }
    }

    suspend fun getCartItems(): List<CartItem> {
        var items =  context.dataStore.data.first()[CART_ITEMS]
        val type = object : TypeToken<List<CartItem>>() {}.type
        val list: List<CartItem> = Gson().fromJson(items, type)
        return list
    }

    suspend fun isLoggedIn(): Boolean {
        return context.dataStore.data
            .map { prefs -> prefs[USER_NAME] }
            .first() != null
    }

    suspend fun getUserName(): String? {
        return context.dataStore.data.first()[USER_NAME]
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}

