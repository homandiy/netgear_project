package com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.homan.huang.netgearmobiledeveloperexercise2021.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ApiServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var apiService: ImageApiService

    @Before
    fun setup() {
        hiltRule.inject()
    }

    // manifest shall have three arrays
    @Test
    fun download_manifest() = runBlockingTest {
        val response = apiService.getManifest()
        val mList = response.body()

    }

}