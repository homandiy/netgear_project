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
import java.io.*
import javax.inject.Inject

class ErrorImageRepository@Inject constructor(
    private val cachedFolder: File,
    private val apiService: ImageApiService,
    private val imageDb: ImageManifestDatabase
): BaseRepository {
    val manifestDao = imageDb.manifestDao()
    val imageDao = imageDb.imageDao()

    //region API service
    // API: download manifest from the server to Manifest POJO
    override
    suspend fun downloadManifest(): Response<ApiManifest> {
        return apiService.getManifest()
    }

    // API: download image from the server to Image POJO
    override
    suspend fun downloadImageData(code: String): Response<ApiImage> {
//        val response = apiService.getImageData(code)
//        lgd("get image of $code: $response")
        return apiService.getImageData(code)
    }

    // API: download image file from api
    override
    suspend fun downloadBitmap(url: String): Response<ResponseBody> {
        return apiService.getImage(url)
    }

    //endregion

    //region Database service
    // Database: get manifest data from room
    override
    suspend fun getManifest(): List<ManifestData>? {
        val groupCount = manifestDao.countCategory()
//        lgd("Group count: $groupCount")

        // check database
        if (groupCount == 0) return null

        return manifestDao.getAll()
    }

    // Database: clean all manifest data in room
    override
    suspend fun clearManifest() {
        manifestDao.deleteAll()
    }

    // Database: copy data from Manifest POJO to room
    override
    suspend fun pojoManifestToDb(manifest: List<List<String>>?) {
        for((index, value) in manifest!!.withIndex()) {
            for (item in value) {
                val category = index+1
                val manifestData = ManifestData(null, category, "Group $category", item)
                manifestDao.insert(manifestData)
            }
        }
    }

    // Database: get image item
    override
    suspend fun getImageDataFromDb(code: String): ImageItem? {
        lgd("repository: get nothing from room")
        return null
    }

    // Database: insert image data from Image POJO to room
    override
    suspend fun pojoImageToDb(body: ApiImage?, code: String) {
        lgd("Do nothing to transfer pojo image to room!")
    }

    // Database: clear image_items table
    override
    suspend fun clearImages() {
        imageDao.deleteAll()
    }

    //endregion

    //region Cache service
    // save download file to cache file
    override
    fun writeResponseBodyToDisk(url: String, body: ResponseBody): Boolean {
        return try {
            var cacheFile = File(cachedFolder, url)
            lgd("repository: cacheFile: $cacheFile")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(cacheFile)
                while (true) {
                    val read: Int = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
//                    lgd( "repository: file download: $fileSizeDownloaded of $fileSize")
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }
                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            false
        }
    }

    // get one image from cache
    override
    fun getImage(url: String): File = File(cachedFolder, url)

    // remove all downloaded images
    override
    fun cleanCache(): Boolean {
        if (cachedFolder.exists())
            for (child in cachedFolder.listFiles())
                child.delete()

        return cachedFolder.listFiles().size == 0
    }

    //endregion

}