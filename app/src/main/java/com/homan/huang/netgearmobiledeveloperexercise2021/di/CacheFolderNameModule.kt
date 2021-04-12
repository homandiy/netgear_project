package com.homan.huang.netgearmobiledeveloperexercise2021.di

import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.CACHE_DIR
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Work on Rest Api
 */
@Module
@InstallIn(SingletonComponent::class)
object CacheFolderNameModule {

    @Provides
    @Singleton
    @Named("cache_dir")
    fun provideCacheFolderName(): String = CACHE_DIR
}