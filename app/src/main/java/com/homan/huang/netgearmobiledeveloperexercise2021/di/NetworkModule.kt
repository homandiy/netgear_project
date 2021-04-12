package com.homan.huang.netgearmobiledeveloperexercise2021.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.homan.huang.netgearmobiledeveloperexercise2021.BuildConfig.API_KEY
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.API_KEY_HEADER
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.CONTENT_HEADER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Work on Rest Api
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // switch to show http log
    val HTTP_DEBUG = true
    // http timeout in second
    val mTimeout = 100L

    val Api_Key_Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header(CONTENT_HEADER, "application/json")
        requestBuilder.header(API_KEY_HEADER, API_KEY)
        chain.proceed(requestBuilder.build())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        if (HTTP_DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(Api_Key_Interceptor)
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .connectTimeout(mTimeout, TimeUnit.SECONDS)
                .build()
        } else // debug OFF
            OkHttpClient.Builder()
                .addInterceptor(Api_Key_Interceptor)
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .connectTimeout(mTimeout, TimeUnit.SECONDS)
                .build()

    @Singleton
    @Provides
    fun providePixabayApi(
        okHttpClient: OkHttpClient,
        gson: Gson,
        baseUrl: String
    ): ImageApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()
            .create(ImageApiService::class.java)
    }
}