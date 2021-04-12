package com.homan.huang.netgearmobiledeveloperexercise2021.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao.ImageDao
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ImageItem
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.di.BaseUrlModule
import com.homan.huang.netgearmobiledeveloperexercise2021.di.CacheFolderNameModule
import com.homan.huang.netgearmobiledeveloperexercise2021.di.DatabaseModule
import com.homan.huang.netgearmobiledeveloperexercise2021.di.RepositoryModule
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.BaseRepository
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ErrorImageRepository
import com.homan.huang.netgearmobiledeveloperexercise2021.util.CoroutineTestRule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


// check if image saved to cache folder
@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@UninstallModules(
    CacheFolderNameModule::class
)
class ImageCacheTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Module
    @InstallIn(SingletonComponent::class)
    class ImageFakeModule {
        // fake cache folder
        @Provides
        @Singleton
        @Named("cache_dir")
        fun provideCacheFolderName(): String = "fake_cache"
    }

    @Inject
    lateinit var cacheFolder: File
    @Inject
    lateinit var apiService: ImageApiService
    @Inject
    lateinit var repository: BaseRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun teardown() {
        repository.cleanCache()
    }

    @Test
    fun save_to_cache() = runBlocking {
        val url = "AdobeStock_391155466.jpg"
        val response = apiService.getImage(url)
        repository.writeResponseBodyToDisk(url, response.body()!!)

        val file = repository.getImage(url)
        assertThat(file!!.exists()).isTrue()
    }

}