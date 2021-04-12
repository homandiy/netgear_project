package com.homan.huang.netgearmobiledeveloperexercise2021.data.local.storage

interface Storage {
    // String
    fun setString(key: String, value: String)
    fun getString(key: String): String

    // Boolean
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean

    // Int
    fun setInt(key: String, value: Int)
    fun getInt(key: String): Int


    fun delKey(key: String): Boolean
    fun clear()
}
