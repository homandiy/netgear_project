package com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.util.CoroutineTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
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

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Inject
    lateinit var apiService: ImageApiService



    @Before
    fun setup() {
        hiltRule.inject()
    }

    // manifest shall have three arrays
    // to prevent it runs forever, timeout with 60s
    @Test
    fun download_manifest() = runBlocking {
        withTimeout(60_000L) {
            val response = apiService.getManifest()
            val mList = response.body()?.manifest
            assertThat(mList?.size).isEqualTo(3)
        }
    }

    /**
     * Test result to contain:
     {
        "name": "Golden Gate 1",
        "url": "http://afternoon-bayou-28316.herokuapp.com/images/AdobeStock_391155466.jpg",
        "type": "jpg",
        "width": 960,
        "height": 540
     }
     */
    @Test
    fun download_image_data() = runBlocking {
        withTimeout(60_000L) {
            val response = apiService.getImageData("a")
            val body = response.body()
            assertThat(body?.url).isEqualTo("http://afternoon-bayou-28316.herokuapp.com/images/AdobeStock_391155466.jpg")
        }
    }

    /**
     * Test image file download: http://afternoon-bayou-28316.herokuapp.com/images/AdobeStock_391155466.jpg
     * size: 377.43kb
     */
    @Test
    fun download_image_file() = runBlocking {
        withTimeout(60_000L) {
            val response = apiService.getImage("AdobeStock_391155466.jpg")
            val size = response.body()?.byteString()?.size
            assertThat(size).isGreaterThan(377000)
        }
    }
}