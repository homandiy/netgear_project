package com.homan.huang.netgearmobiledeveloperexercise2021.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.homan.huang.netgearmobiledeveloperexercise2021.BuildConfig.API_KEY
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.di.BaseUrlModule
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.API_KEY_HEADER
import com.homan.huang.netgearmobiledeveloperexercise2021.util.CoroutineTestRule
import com.homan.huang.netgearmobiledeveloperexercise2021.util.json.getImageJson
import com.homan.huang.netgearmobiledeveloperexercise2021.util.json.getManifestJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

import okhttp3.mockwebserver.RecordedRequest

/**
 * Check Api Key
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(BaseUrlModule::class)
class HttpTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Inject
    lateinit var apiService: ImageApiService

    @Module
    @InstallIn(SingletonComponent::class)
    class FakeBaseUrlModule {
        @Provides
        fun provideUrl(): String = "http://localhost:8080/"
    }

    // Mock Webserver
    protected lateinit var mockWebServer: MockWebServer
    private fun getDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/manifest" -> MockResponse().setResponseCode(200)
                        .setBody(getManifestJson())
                    "/image" -> MockResponse().setResponseCode(200)
                        .setBody(getImageJson())
                    else -> MockResponse().setResponseCode(400)
                }
            }
        }
    }
        

    @Before
    fun setup() {
        hiltRule.inject()

        // Web server
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.dispatcher = getDispatcher()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // manifest shall have three arrays
    // to prevent it runs forever, timeout with 60s
    @Test
    fun check_api_key() = runBlocking {

        val response = apiService.getManifest()

        val request = mockWebServer.takeRequest()
        val key = request.getHeader(API_KEY_HEADER)

        assertThat(key).isEqualTo(API_KEY)
    }

}