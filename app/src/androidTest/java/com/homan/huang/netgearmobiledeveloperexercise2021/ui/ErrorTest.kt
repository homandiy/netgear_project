package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.google.common.truth.Truth.assertThat
import com.homan.huang.netgearmobiledeveloperexercise2021.R
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ErrorTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    // empty fragment test in Hilt
    @Test
    fun clickDirectionButtonToListImagesFragment() {
//        val navController = TestNavHostController(
//            ApplicationProvider.getApplicationContext())
//
//        runOnUiThread {
//            navController.setGraph(R.navigation.nav_graph)
//        }
//
//        launchFragmentInHiltContainer<ImageGroupFragment> {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        onView(withId(R.id.bt_direction)).perform(ViewActions.click())
//        assertThat(navController.currentDestination?.id).isEqualTo(R.id.listImagesFragment2)
    }

    fun recyclerViewTest() {
        val listManifest = listOf<ManifestData>(
            ManifestData(111, 4, "4", "j"),
            ManifestData(112, 5, "5", "k"),
            ManifestData(113, 6, "6", "l"),
        )
//        manifestAdapter.setData(listManifest)
    }

}