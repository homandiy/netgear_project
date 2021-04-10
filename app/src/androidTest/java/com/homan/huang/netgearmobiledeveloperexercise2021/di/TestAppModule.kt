package com.homan.huang.netgearmobiledeveloperexercise2021.di

import android.content.Context
import androidx.room.Room
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.ImageManifestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ImageManifestDatabase::class.java)
            .allowMainThreadQueries()
            .build()

}