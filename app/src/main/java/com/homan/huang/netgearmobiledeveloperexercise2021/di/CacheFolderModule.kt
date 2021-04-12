package com.homan.huang.netgearmobiledeveloperexercise2021.di

import android.content.Context
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.CACHE_DIR
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * Work on Rest Api
 */
@Module
@InstallIn(SingletonComponent::class)
object CacheFolderModule {

    @Provides
    @Singleton
    fun provideCacheFolder(
        @Named("cache_dir") folderName: String,
        @ApplicationContext context:Context
    ): File = context.getDir(folderName, Context.MODE_PRIVATE)
}