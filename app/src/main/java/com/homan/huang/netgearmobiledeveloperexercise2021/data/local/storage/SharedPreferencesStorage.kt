package com.homan.huang.netgearmobiledeveloperexercise2021.data.local.storage

import android.content.Context
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.ERROR
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

// @Inject tells Dagger how to provide instances of this type
class SharedPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context,
    @Named("storage") private val fileName: String
) : Storage {

    private val sharedPreferences = context
        .getSharedPreferences(fileName, Context.MODE_PRIVATE)

    // String
    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, ERROR)!!
    }

    // Boolean
    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)!!
    }

    // Integer
    override fun setInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)!!
    }

    // Other
    override fun delKey(key: String): Boolean {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
        return !sharedPreferences.contains(key)
    }

    override fun clear() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}
