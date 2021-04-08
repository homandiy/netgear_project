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

    // count category
    @Query("SELECT COUNT(DISTINCT(category_id)) FROM manifest")
    suspend fun countCategory(): Int

    // Get
    @Query("SELECT * FROM manifest")
    fun observeAllManifest(): LiveData<List<Manifest>>

}