package com.homan.huang.netgearmobiledeveloperexercise2021.repository

import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.ImageManifestDatabase
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository  @Inject constructor(
    private val imageApiService: ImageApiService,
    private val imageDb: ImageManifestDatabase
) {

    val manifestDao = imageDb.manifestDao()
    val imageDao = imageDb.imageDao()

    suspend fun getManifest(): List<ManifestData>? {
        val groupCount = manifestDao.countCategory()
        lgd("Group count: $groupCount")

        return null
    }


}