package com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import java.util.jar.Manifest

@Dao
interface ManifestDao {

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(manifest: ManifestData)

    // Delete
    @Delete
    suspend fun delete(manifest: ManifestData)

    // Delete all
    @Query("DELETE FROM manifest")
    suspend fun deleteAll()

    // Count category
    @Query("SELECT COUNT(category_id) FROM manifest")
    suspend fun countCategory(): Int

    // Get all image manifest
    @Query("SELECT * FROM manifest")
    suspend fun getAll(): List<ManifestData>

}