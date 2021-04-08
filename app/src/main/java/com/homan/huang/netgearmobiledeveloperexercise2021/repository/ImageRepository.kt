package com.homan.huang.netgearmobiledeveloperexercise2021.repository

import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.ImageManifestDatabase
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository  @Inject constructor(
    private val imageApiService: ImageApiService,
    private val imageDb: ImageManifestDatabase
) {

}