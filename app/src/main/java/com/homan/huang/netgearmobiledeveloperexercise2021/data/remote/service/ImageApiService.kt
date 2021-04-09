package com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service

import android.graphics.Bitmap
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.pojo.ApiImage
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.pojo.ApiManifest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApiService {

    /**
     * Get image manifest in array
     * Url Example: https://afternoon-bayou-28316.herokuapp.com/manifest
     */
    @GET("/manifest")
    suspend fun getManifest(): Response<ApiManifest>

    /**
     * Get image image from a single file
     * Url Example: https://afternoon-bayou-28316.herokuapp.com/image/a
     */
    @GET("/image/{ImageId}")
    suspend fun getImageData(@Path("ImageId") imageId: String): Response<ApiImage>

    /**
     * Get image image from a single file
     * Url Example: http://afternoon-bayou-28316.herokuapp.com/images/AdobeStock_391155534.png
     */
    @GET("/images/{ImageFile}")
    suspend fun getImage(@Path("ImageFile") imageId: String): Response<Bitmap>

}