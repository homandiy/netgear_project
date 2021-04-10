package com.homan.huang.netgearmobiledeveloperexercise2021.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.homan.huang.netgearmobiledeveloperexercise2021.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
//@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ThirdGroupTest {

    @get:Rule(order = 0)
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE")

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun thirdGroupTest() {
        val materialButton = onView(
allOf(withId(R.id.next_group_bt), withText("Next"),
childAtPosition(
allOf(withId(R.id.cl_present),
childAtPosition(
withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
0)),
4),
isDisplayed()))
        materialButton.perform(click())
        
        val materialButton2 = onView(
allOf(withId(R.id.next_group_bt), withText("Next"),
childAtPosition(
allOf(withId(R.id.cl_present),
childAtPosition(
withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
0)),
4),
isDisplayed()))
        materialButton2.perform(click())

        Thread.sleep(2000) // wait for 2s or fail
        
        val textView = onView(
allOf(withText("Netgear Mobile Developer Exercise 2021"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()))
        textView.check(matches(withText("Netgear Mobile Developer Exercise 2021")))
        
        val button = onView(
allOf(withId(R.id.back_bt),
withParent(allOf(withId(R.id.cl_present),
withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)))),
isDisplayed()))
        button.check(matches(isDisplayed()))
        
        val textView2 = onView(
allOf(withId(R.id.tv_group_num), withText("GROUP 3"),
withParent(allOf(withId(R.id.cl_present),
withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)))),
isDisplayed()))
        textView2.check(matches(withText("GROUP 3")))
        
        val imageView = onView(
allOf(withId(R.id.small_image),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        imageView.check(matches(isDisplayed()))
        
        val textView3 = onView(
allOf(withId(R.id.image_name), withText("Mockup Design"),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        textView3.check(matches(withText("Mockup Design")))
        
        val button2 = onView(
allOf(withId(R.id.roll_right_bt),
withParent(allOf(withId(R.id.control_image_bar),
withParent(withId(R.id.cl_present)))),
isDisplayed()))
        button2.check(matches(isDisplayed()))
        
        val button3 = onView(
allOf(withId(R.id.roll_right_bt),
withParent(allOf(withId(R.id.control_image_bar),
withParent(withId(R.id.cl_present)))),
isDisplayed()))
        button3.check(matches(isDisplayed()))
        
        val materialButton3 = onView(
allOf(withId(R.id.roll_right_bt),
childAtPosition(
allOf(withId(R.id.control_image_bar),
childAtPosition(
withId(R.id.cl_present),
3)),
1),
isDisplayed()))
        materialButton3.perform(click())

        Thread.sleep(2000) // wait for 2s or fail
        
        val imageView2 = onView(
allOf(withId(R.id.small_image),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        imageView2.check(matches(isDisplayed()))
        
        val textView4 = onView(
allOf(withId(R.id.image_name), withText("Blurred City"),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        textView4.check(matches(withText("Blurred City")))
        
        val button4 = onView(
allOf(withId(R.id.roll_left_bt),
withParent(allOf(withId(R.id.control_image_bar),
withParent(withId(R.id.cl_present)))),
isDisplayed()))
        button4.check(matches(isDisplayed()))
        
        val button5 = onView(
allOf(withId(R.id.roll_right_bt),
withParent(allOf(withId(R.id.control_image_bar),
withParent(withId(R.id.cl_present)))),
isDisplayed()))
        button5.check(matches(isDisplayed()))
        
        val button6 = onView(
allOf(withId(R.id.roll_right_bt),
withParent(allOf(withId(R.id.control_image_bar),
withParent(withId(R.id.cl_present)))),
isDisplayed()))
        button6.check(matches(isDisplayed()))
        
        val materialButton4 = onView(
allOf(withId(R.id.roll_right_bt),
childAtPosition(
allOf(withId(R.id.control_image_bar),
childAtPosition(
withId(R.id.cl_present),
3)),
1),
isDisplayed()))
        materialButton4.perform(click())

        Thread.sleep(2000) // wait for 2s or fail
        
        val imageView3 = onView(
allOf(withId(R.id.small_image),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        imageView3.check(matches(isDisplayed()))
        
        val textView5 = onView(
allOf(withId(R.id.image_name), withText("Forest and Mountains"),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        textView5.check(matches(withText("Forest and Mountains")))
        
        val button7 = onView(
allOf(withId(R.id.roll_left_bt),
withParent(allOf(withId(R.id.control_image_bar),
withParent(withId(R.id.cl_present)))),
isDisplayed()))
        button7.check(matches(isDisplayed()))
        
        val button8 = onView(
allOf(withId(R.id.roll_left_bt),
withParent(allOf(withId(R.id.control_image_bar),
withParent(withId(R.id.cl_present)))),
isDisplayed()))
        button8.check(matches(isDisplayed()))
        
        val materialButton5 = onView(
allOf(withId(R.id.roll_left_bt),
childAtPosition(
allOf(withId(R.id.control_image_bar),
childAtPosition(
withId(R.id.cl_present),
3)),
0),
isDisplayed()))
        materialButton5.perform(click())

        Thread.sleep(2000) // wait for 2s or fail
        
        val textView6 = onView(
allOf(withId(R.id.image_name), withText("Blurred City"),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        textView6.check(matches(withText("Blurred City")))
        
        val materialButton6 = onView(
allOf(withId(R.id.roll_left_bt),
childAtPosition(
allOf(withId(R.id.control_image_bar),
childAtPosition(
withId(R.id.cl_present),
3)),
0),
isDisplayed()))
        materialButton6.perform(click())

        Thread.sleep(2000) // wait for 2s or fail
        
        val textView7 = onView(
allOf(withId(R.id.image_name), withText("Mockup Design"),
withParent(withParent(withId(R.id.carView))),
isDisplayed()))
        textView7.check(matches(withText("Mockup Design")))
        }
    
    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    }
