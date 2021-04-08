package com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Create a manifest table in Room to categorize images.
 */
@Entity(tableName = "manifest")
data class ManifestData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val category_id: Int,
    var category_name: String,
    val code: String
)