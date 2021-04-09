package com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Create a image_items tabel to store image data in Room
 */
@Entity(
     tableName = "image_items",
     indices = [Index(value = ["code"], unique = true)]
)
data class ImageItem(
     @PrimaryKey(autoGenerate = true)
     val id: Int? = null,
     val code:String, // code name for restapi
     val height: Int,
     val name: String,
     val type: String,
     val url: String,
     val width: Int
)