package com.homan.huang.netgearmobiledeveloperexercise2021.repository

import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.ImageManifestDatabase
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ImageItem
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.pojo.ApiImage
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.pojo.ApiManifest
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository  @Inject constructor(
    private val apiService: ImageApiService,
    private val imageDb: ImageManifestDatabase
) {

    val manifestDao = imageDb.manifestDao()
    val imageDao = imageDb.imageDao()

    // API: download manifest from the server to Manifest POJO
    suspend fun downloadManifest(): Response<ApiManifest> {
        return apiService.getManifest()
    }

    // Database: get manifest data from room
    suspend fun getManifest(): List<ManifestData>? {
        val groupCount = manifestDao.countCategory()
        lgd("Group count: $groupCount")

        // check database
        if (groupCount == 0) return null

        return manifestDao.getAll()
    }

    // Database: clean all manifest data in room
    suspend fun clearManifest() {
        manifestDao.deleteAll()
    }

    // Database: copy data from Manifest POJO to room
    suspend fun manifestToDb(body: ApiManifest?) {
        val groups = body!!.manifest
        for((index, value) in groups.withIndex()) {
            lgd("idx: $index, value: $value")

            for (item in value) {
                val category = index+1
                val manifestData = ManifestData(null, category, "Group $category", item)
                manifestDao.insert(manifestData)
            }
        }
    }

    // Database: get image item
    suspend fun getImageDataFromDb(code: String): ImageItem {
        val imageItem = imageDao.getImageItem(code)
        lgd("repository: image item($code): $imageItem")

        return imageItem
    }

    // API: download image from the server to Image POJO
    suspend fun downloadImageData(code: String): Response<ApiImage> {
        return apiService.getImageData(code)
    }

    // Database: insert image data from Image POJO to room
    suspend fun imageDataToDb(body: ApiImage?, code: String) {
        val imageItem = ImageItem(
            null,
            code,
            body!!.height,
            body.name,
            body.type,
            body.url.substringAfter("/images/"),
            body.width
        )

        lgd("image data: $imageItem")

        // insert to room
        imageDao.insert(imageItem)
    }

    // Database: clear image_items table
    suspend fun clearImages() {
        imageDao.deleteAll()
    }


}