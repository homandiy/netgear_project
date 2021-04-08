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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //region RestApi call
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
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .connectTimeout(mTimeout, TimeUnit.SECONDS)
                .build()
        } else // debug OFF
            OkHttpClient.Builder()
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .connectTimeout(mTimeout, TimeUnit.SECONDS)
                .build()

    @Singleton
    @Provides
    fun providePixabayApi(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): ImageApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
            .create(ImageApiService::class.java)
    }
    //endregion

    @Singleton
    @Provides
    fun provideImageManifestDatabase(
        @ApplicationContext context: Context
    ) : ImageManifestDatabase = Room.databaseBuilder(
                                    context,
                                    ImageManifestDatabase::class.java,
                                    DATABASE_NAME
                                ).build()

    @Singleton
    @Provides
    fun provideImageRepository(
        imageApiService: ImageApiService,
        imageDb: ImageManifestDatabase
    ) = ImageRepository(imageApiService, imageDb)



}