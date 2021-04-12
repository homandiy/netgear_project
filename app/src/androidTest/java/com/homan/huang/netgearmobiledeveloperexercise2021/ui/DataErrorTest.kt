package com.homan.huang.netgearmobiledeveloperexercise2021.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DataErrorTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.INTERNET",
"android.permission.ACCESS_NETWORK_STATE",
"android.permission.READ_EXTERNAL_STORAGE",
"android.permission.WRITE_EXTERNAL_STORAGE")

    @Test
    fun dataErrorTest() {
        val textView = onView(
allOf(IsInstanceOf.instanceOf(android.widget.TextView::class.java), withText("Alert! Data Error..."),
withParent(allOf(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java)))),
isDisplayed()))
        textView.check(matches(withText("Alert! Data Error...")))
        
        val textView2 = onView(
allOf(withId(android.R.id.message), withText("Please clean your internal memory!\nCLOSE?"),
withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
isDisplayed()))
        textView2.check(matches(withText("Please clean your internal memory! CLOSE?")))
        
        val button = onView(
allOf(withId(android.R.id.button2), withText("TRY AGAIN!"),
withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
isDisplayed()))
        button.check(matches(isDisplayed()))
        }
    }
