package com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ImageItem
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import java.util.jar.Manifest

@Dao
interface ImageDao {

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageItem: ImageItem)

    // Delete
    @Delete
    suspend fun delete(imageItem: ImageItem)

    // count total images
    @Query("SELECT COUNT(*) FROM image_items")
    suspend fun countCategory(): Int

    // Get images in same category
    @Query("SELECT * FROM image_items WHERE code = :code")
    suspend fun observeImagesInSameCategory(code: String): LiveData<List<ImageItem>>

}