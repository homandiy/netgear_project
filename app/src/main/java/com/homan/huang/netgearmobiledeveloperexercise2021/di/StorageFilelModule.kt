package com.homan.huang.netgearmobiledeveloperexercise2021.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.homan.huang.netgearmobiledeveloperexercise2021.BuildConfig.API_KEY
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.ImageManifestDatabase
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.BASE_URL
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.DATABASE_NAME
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Provide file name for SharedPreferences Storage
 */
@Module
@InstallIn(SingletonComponent::class)
object StorageFilelModule {

    @Provides
    @Singleton
    @Named("storage")
    fun provideFileName(): String = "storage_04092021"
}