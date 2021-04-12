package com.homan.huang.netgearmobiledeveloperexercise2021.ui.error

import android.content.Context
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.ImageManifestDatabase
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao.ManifestDao
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.data.remote.service.ImageApiService
import com.homan.huang.netgearmobiledeveloperexercise2021.di.*
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.BaseRepository
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ErrorImageRepository
import com.homan.huang.netgearmobiledeveloperexercise2021.ui.MainActivity
import com.homan.huang.netgearmobiledeveloperexercise2021.util.json.getManifestJson
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
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.google.common.truth.Truth
import com.homan.huang.netgearmobiledeveloperexercise2021.BuildConfig
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.dao.ImageDao

import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.ERRMSG_IMAGE_DATA
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.ERRMSG_IMAGE_READING
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.util.CoroutineTestRule
import com.homan.huang.netgearmobiledeveloperexercise2021.util.ToastMatcher
import com.homan.huang.netgearmobiledeveloperexercise2021.util.json.getImageJson


/**
 * Trigger the UI Error: Imange Download Error
 *                       Download for 3 times to get the
 *                       right data.
 * Condition:
 *      ApiImage = [] by MockWebserver
 *      image_items = empty by DownloadErrorRepository
 */
@ExperimentalCoroutinesApi
@LargeTest
@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
@UninstallModules(
    RepositoryModule::class,
    CacheFolderNameModule::class,
    BaseUrlModule::class,
    DatabaseModule::class,
)
class ImageDownloadErrorTest {
    @get:Rule(order = 0)
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE")

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 3)
    var activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Module
    @InstallIn(SingletonComponent::class)
    class ImageFakeModule {
        // fake cache folder
        @Provides
        @Singleton
        @Named("cache_dir")
        fun provideCacheFolderName(): String = "fake_cache"

        // fake url
        @Provides
        @Singleton
        fun provideUrl(): String = "http://localhost:8080/"

        // fake database
        @Provides
        @Singleton
        fun provideInMemoryDb(@ApplicationContext context: Context) =
            Room.inMemoryDatabaseBuilder(context, ImageManifestDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        // fake repository
        @Provides
        @Singleton
        fun provideFakeRepository(
            imageCachedFolder: File,
            imageApiService: ImageApiService,
            imageDb: ImageManifestDatabase
        ): BaseRepository =
            ErrorImageRepository(imageCachedFolder, imageApiService, imageDb)

    }

    @Inject
    lateinit var db: ImageManifestDatabase
    private lateinit var manifestDao: ManifestDao



    private val toastMatcher = ToastMatcher()

    // Mock Webserver
    protected lateinit var mockWebServer: MockWebServer

    // Mock: Good manifest data and bad image data
    private fun getBadImageDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/manifest" -> MockResponse().setResponseCode(200)
                        .setBody(getManifestJson())
                    "/image" -> MockResponse().setResponseCode(200)
                        .setBody("{}")
                    else -> MockResponse().setResponseCode(400)
                }
            }
        }
    }

    private fun getGoodImageDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/manifest" -> MockResponse().setResponseCode(200)
                        .setBody(getManifestJson())
                    "/image/a" -> MockResponse().setResponseCode(200)
                        .setBody(getImageJson())
                    else -> MockResponse().setResponseCode(400)
                }
            }
        }
    }

    @Before
    fun setup() {
        hiltRule.inject()

        manifestDao = db.manifestDao()
        runBlocking {
            manifestDao.insert(ManifestData(1, 1, "Group 1", "a"))
            manifestDao.insert(ManifestData(2, 2, "Group 2", "b"))
            manifestDao.insert(ManifestData(3, 2, "Group 2", "c"))
        }
    }

    @After
    fun teardonw() {
        mockWebServer.shutdown()
    }

    // download image error with toast message
    @Test
    fun test_image_data_download_error_toast() {
        // Web server
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = getBadImageDispatcher()
        // start web server
        mockWebServer.start(8080)

        val toastMatcher = ToastMatcher()

        // start activity
        val scenario = activityRule.getScenario()

        Thread.sleep(2000L)

        // espresso: verify dialog data
        onView(withText(ERRMSG_IMAGE_DATA))
            .inRoot(toastMatcher)
            .check(matches(isDisplayed()))

        Thread.sleep(2000L)
    }

    // save to image to database error with toast message
    @Test
    fun test_image_read_database_error_toast() {
        // Web server
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = getGoodImageDispatcher()
        // start web server
        mockWebServer.start(8080)

        val toastMatcher = ToastMatcher()

        // start activity
        val scenario = activityRule.getScenario()

        Thread.sleep(2000L)

        // espresso: verify dialog data
        onView(withText(ERRMSG_IMAGE_READING))
            .inRoot(toastMatcher)
            .check(matches(isDisplayed()))

        Thread.sleep(2000L)
    }


}