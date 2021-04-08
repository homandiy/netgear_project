package com.homan.huang.netgearmobiledeveloperexercise2021.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao.ImageDao
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao.ManifestDao
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ImageItem
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData

@Database(
    entities = [
        ManifestData::class,
        ImageItem::class
    ],
    version = 1
)
abstract class ImageManifestDatabase : RoomDatabase() {

    abstract fun manifestDao(): ManifestDao

    abstract fun imageDao(): ImageDao

}