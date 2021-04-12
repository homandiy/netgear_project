package com.homan.huang.netgearmobiledeveloperexercise2021.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

object Constants {

    // Api Server
    const val API_KEY_HEADER = "X-API-KEY"
    const val CONTENT_HEADER = "Content-Type"
    const val BASE_URL = "https://afternoon-bayou-28316.herokuapp.com/"

    // Room
    const val DATABASE_NAME = "image_manifest_db"

    // Livedata: error
    const val ERRMSG_SERVER = "Server Error!\nCLOSE?"
    const val ERRMSG_DOWNLOAD = "Download error! Continue with old data?"
    const val ERRMSG_INTERNAL_DATA = "Please clean your internal memory!\nCLOSE?"
    const val ERRMSG_IMAGE_DATA = "Image Data Download Error!"
    const val ERRMSG_IMAGE_READING = "Problem to read local storage!"
    const val ERRMSG_IMAGE_DOWNLOAD = "Image File Download Error!"

    // cache
    const val CACHE_DIR = "app_cache_04092021"

    // Storage
    const val ERROR = "error"
    // other storage string
    const val LOADING = "loading"
    const val DONE = "done"
    const val LAST_DATE = "last_date"
    // data expiration period (type: long)
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    const val EXPIRED_DAY = 1L
}