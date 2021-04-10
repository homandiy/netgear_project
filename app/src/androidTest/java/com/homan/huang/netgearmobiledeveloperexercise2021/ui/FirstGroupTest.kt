package com.homan.huang.netgearmobiledeveloperexercise2021.ui


import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.homan.huang.netgearmobiledeveloperexercise2021.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
//@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FirstGroupTest {

    @get:Rule(order = 0)
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun firstPageTest() {
        Thread.sleep(2000) // wait for 2s or fail

        val textView = onView(
            allOf(
                withId(R.id.tv_group_num), withText("GROUP 1"),
                withParent(
                    allOf(
                        withId(R.id.cl_present),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("GROUP 1")))

        val button = onView(
            allOf(
                withId(R.id.next_group_bt), withText("NEXT"),
                withParent(
                    allOf(
                        withId(R.id.cl_present),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.image_name), withText("Golden Gate 1"),
                withParent(withParent(withId(R.id.carView))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Golden Gate 1")))

    }
}