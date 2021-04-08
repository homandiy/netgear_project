package com.homan.huang.netgearmobiledeveloperexercise2021.di

import android.content.Context
import androidx.room.Room
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // switch to show http log
    val HTTP_DEBUG = true
    // http timeout in second
    val mTimeout = 100L

    val App_Key_Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.header("X-API-KEY", API_KEY)
        chain.proceed(requestBuilder.build())
    }

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
            context,
            ImageManifestDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        imageApiService: ImageApiService,
        imageDb: ImageManifestDatabase
    ) = ImageRepository(imageApiService, imageDb)


    @Singleton
    @Provides
    fun providePixabayApi(): ImageApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ImageApiService::class.java)
    }
}