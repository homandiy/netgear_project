package com.homan.huang.netgearmobiledeveloperexercise2021.repository

import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.ImageManifestDatabase
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ImageItem
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.pojo.ApiImage
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.pojo.ApiManifest
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

interface BaseRepository {

    //region API service
    // API: download manifest from the server to Manifest POJO
    suspend fun downloadManifest(): Response<ApiManifest>

    // API: download image from the server to Image POJO
    suspend fun downloadImageData(code: String): Response<ApiImage>

    // API: download image file from api
    suspend fun downloadBitmap(url: String): Response<ResponseBody>

    //endregion

    //region Database service
    // Database: get manifest data from room
    suspend fun getManifest(): List<ManifestData>?

    // Database: clean all manifest data in room
    suspend fun clearManifest()

    // Database: copy data from Manifest POJO to room
    suspend fun pojoManifestToDb(manifest: List<List<String>>?)

    // Database: get image item
    suspend fun getImageDataFromDb(code: String): ImageItem?

    // Database: insert image data from Image POJO to room
    suspend fun pojoImageToDb(body: ApiImage?, code: String)

    // Database: clear image_items table
    suspend fun clearImages()

    //endregion

    //region Cache
    // save download file to cache file
    fun writeResponseBodyToDisk(url: String, body: ResponseBody): Boolean

    fun getImage(url: String): File?

    fun cleanCache(): Boolean

    //endregion
}