package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.homan.huang.netgearmobiledeveloperexercise2021.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FragmentHiltTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // single task rule
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    // empty fragment test in Hilt
    @Test
    fun testLaunchFragment() {
        launchFragmentInHiltContainer<ImageGroupFragment> {
        }
    }


}